package com.serhiiromanchuk.utilitybills.domain.usecase.bill_package

import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillPackagesUseCase @Inject constructor(
    private val repository: BillPackageRepository
) {

    operator fun invoke(): Flow<List<BillPackage>> {
        return repository.getBillPackages()
    }
}