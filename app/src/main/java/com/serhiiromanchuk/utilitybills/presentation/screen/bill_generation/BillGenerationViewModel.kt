package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillCountUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithServicesUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetLastBillWithServicesUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetBillPackageWithBillsUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.DeleteUtilityServiceFromBillUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.InsertUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.DialogState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.PackageState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.utils.MeterValueType
import com.serhiiromanchuk.utilitybills.utils.mergeWith
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class BillGenerationViewModel @Inject constructor(
    private val billPackageId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithServicesUseCase,
    private val deleteUtilityServiceFromBillUseCase: DeleteUtilityServiceFromBillUseCase,
    private val getLastBillWithServicesUseCase: GetLastBillWithServicesUseCase,
    private val getBillCountUseCase: GetBillCountUseCase,
    private val getBillPackageWithBillsUseCase: GetBillPackageWithBillsUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase,
    private val insertUtilityServiceUseCase: InsertUtilityServiceUseCase
) : ViewModel() {

    private val bufferUtilityServicesList = mutableStateListOf<ServiceItemState>()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(BillGenerationUiState())
    private val refreshDate = MutableSharedFlow<LocalDate>(replay = 1)
    private val refreshBill = MutableSharedFlow<Long>(replay = 1)

    private val billWithServicesFlow = flow {
        refreshBill.collect { billId ->
            getBillWithUtilityServicesUseCase(billId).collect { currentBill ->
                if (bufferUtilityServicesList.isNotEmpty()) bufferUtilityServicesList.clear()
                currentBill.utilityServices.forEach { utilityService ->
                    bufferUtilityServicesList.add(ServiceItemState(utilityService))
                }
                emit(
                    _screenState.value.copy(
                        bill = currentBill.bill,
                        serviceStateList = bufferUtilityServicesList
                    )
                )
            }
        }
    }

    private val dateFlow = flow {
        refreshDate.collect { date ->
            val billList = _screenState.value.packageState.billList
            val bill = billList.firstOrNull { it.date.month == date.month }
            bill?.let { refreshBill.emit(it.id) }

            val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale.getDefault())
            emit(_screenState.value.copy(date = date.format(formatter)))
        }
    }

    val screenState: StateFlow<BillGenerationUiState> = _screenState
        .mergeWith(billWithServicesFlow)
        .mergeWith(dateFlow)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = BillGenerationUiState()
        )


    init {
        val lastBillDeferred = viewModelScope.async {
            getLastBillWithServicesUseCase(billPackageId)
                ?: throw Exception("Package with id:$billPackageId is empty")
        }
        val checkDateJob = viewModelScope.launch {
            val lastBill = lastBillDeferred.await()
            val lastDate = lastBill.bill.date
            val currentDate = LocalDate.now()

            val period = Period.between(lastDate, currentDate)
            val monthsPassed = if (lastDate.dayOfMonth > currentDate.dayOfMonth) {
                period.toTotalMonths().plus(1).toInt()
            } else {
                period.toTotalMonths().toInt()
            }

            // If the current month is the next from the date in the last bill, we create a new
            // bill and enter the last values of the utility meters of the previous bill.
            // If the difference between the months is greater, then we create blank bills for
            // the missing months
            if (monthsPassed == 1) {
                val createBillJob = viewModelScope.launch {
                    val newBill = Bill(
                        packageCreatorId = billPackageId,
                        date = currentDate
                    )
                    insertBillItemUseCase(newBill)
                }
                val newBillDeferred = viewModelScope.async {
                    createBillJob.join()
                    getLastBillWithServicesUseCase(billPackageId)
                        ?: throw Exception("Package with id:$billPackageId is empty")
                }

                val newBill = newBillDeferred.await()
                lastBill.utilityServices.forEach { previousService ->
                    val newService = UtilityService(
                        billCreatorId = newBill.bill.id,
                        name = previousService.name,
                        tariff = previousService.tariff,
                        isMeterAvailable = previousService.isMeterAvailable,
                        previousValue = previousService.currentValue,
                        unitOfMeasurement = previousService.unitOfMeasurement,
                        indexPosition = previousService.indexPosition
                    )
                    val insertServiceJob = viewModelScope.launch {
                        insertUtilityServiceUseCase(newService)
                    }
                    insertServiceJob.join()
                }

            } else if (monthsPassed > 1) {

                val firstLostDate = currentDate.minusMonths(monthsPassed - 1L)

                repeat(monthsPassed) {
                    val blankBill = Bill(
                        packageCreatorId = billPackageId,
                        date = firstLostDate.plusMonths(it.toLong())
                    )
                    val insertBillJob = viewModelScope.launch {
                        insertBillItemUseCase(blankBill)
                    }
                    insertBillJob.join()
                }
            }
        }

        viewModelScope.launch {
            checkDateJob.join()
            getBillPackageWithBillsUseCase(billPackageId).collect { packageWithBills ->
                _screenState.update {
                    it.copy(
                        packageState = PackageState(
                            billList = packageWithBills.sortedBills,
                            payerName = packageWithBills.billPackage.payerName,
                            address = packageWithBills.billPackage.address
                        )
                    )
                }
                refreshDate.emit(LocalDate.now())
            }
        }
    }


    fun onEvent(event: BillGenerationUiEvent) {
        when (event) {
            is BillGenerationUiEvent.AddUtilityService -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnAddService(event.billCreatorId))
                }
            }

            is BillGenerationUiEvent.DeleteUtilityService -> {
                viewModelScope.launch {
                    deleteUtilityServiceFromBillUseCase(billPackageId, event.serviceId)
                    _screenState.update { it.copy(dialogState = DialogState.Close) }
                }
            }

            BillGenerationUiEvent.EditBillInfo -> TODO()

            BillGenerationUiEvent.Submit -> TODO()

            BillGenerationUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnBack)
                }
            }

            is BillGenerationUiEvent.CheckStateChanged -> {
                changeServiceCheckedState(event.serviceId, event.checked)
            }

            is BillGenerationUiEvent.CurrentValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.CURRENT)
            }

            is BillGenerationUiEvent.PreviousValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.PREVIOUS)
            }

            is BillGenerationUiEvent.OnEditServiceClicked -> TODO()

            is BillGenerationUiEvent.OpenDialog -> {
                _screenState.update { it.copy(dialogState = DialogState.Open(event.service)) }
            }

            BillGenerationUiEvent.CloseDialog -> {
                _screenState.update { it.copy(dialogState = DialogState.Close) }
            }
        }
    }

    private fun meterValueChange(id: Long, value: String, meterValueType: MeterValueType) {
        bufferUtilityServicesList.apply {
            replaceAll { oldServiceState ->
                val oldServiceItem = oldServiceState.utilityServiceItem

                if (oldServiceItem.id == id) {
                    val newServiceItem = when (meterValueType) {
                        MeterValueType.PREVIOUS -> {
                            oldServiceItem.copy(previousValue = value)
                        }

                        MeterValueType.CURRENT -> {
                            oldServiceItem.copy(currentValue = value)
                        }
                    }
                    oldServiceState.copy(utilityServiceItem = newServiceItem)
                } else {
                    oldServiceState
                }
            }
        }
        updateList()
    }

    private fun changeServiceCheckedState(id: Long, isChecked: Boolean) {
        bufferUtilityServicesList.apply {
            replaceAll { oldServiceState ->
                if (oldServiceState.utilityServiceItem.id == id) {
                    oldServiceState.copy(isChecked = isChecked)
                } else {
                    oldServiceState
                }
            }
        }
        updateList()
    }

    private fun updateList() {
        _screenState.update {
            it.copy(serviceStateList = bufferUtilityServicesList)
        }
    }

    sealed interface NavigationEvent {
        data object OnBack : NavigationEvent

        data class OnAddService(
            val billCreatorId: Long
        ) : NavigationEvent

        data class OnEditService(
            val serviceId: Long,
            val billCreatorId: Long
        ) : NavigationEvent

        data object OnCreateBill : NavigationEvent
    }
}