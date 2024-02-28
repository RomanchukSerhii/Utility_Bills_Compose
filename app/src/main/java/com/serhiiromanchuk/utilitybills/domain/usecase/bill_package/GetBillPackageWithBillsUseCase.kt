package com.serhiiromanchuk.utilitybills.domain.usecase.bill_package

import com.serhiiromanchuk.utilitybills.domain.model.PackageWithBills
import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillPackageWithBillsUseCase @Inject constructor(
    private val repository: BillPackageRepository
) {

    operator fun invoke(packageId: Long): Flow<PackageWithBills> {
        return repository.getBillPackageWitBills(packageId)
    }
}