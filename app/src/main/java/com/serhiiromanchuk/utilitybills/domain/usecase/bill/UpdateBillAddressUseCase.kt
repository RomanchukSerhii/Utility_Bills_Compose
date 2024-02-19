package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class UpdateBillAddressUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(address: String, id: Long) {
        repository.updateBillAddress(address, id)
    }
}