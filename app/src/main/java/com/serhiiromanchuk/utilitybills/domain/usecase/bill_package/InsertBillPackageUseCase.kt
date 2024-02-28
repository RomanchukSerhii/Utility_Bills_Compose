package com.serhiiromanchuk.utilitybills.domain.usecase.bill_package

import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import javax.inject.Inject

class InsertBillPackageUseCase @Inject constructor(
    private val repository: BillPackageRepository
) {
    suspend operator fun invoke(billPackage: BillPackage) {
        repository.insertBillPackage(billPackage)
    }
}