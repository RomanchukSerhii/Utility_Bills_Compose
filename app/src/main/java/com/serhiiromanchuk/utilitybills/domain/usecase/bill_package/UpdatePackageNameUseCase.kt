package com.serhiiromanchuk.utilitybills.domain.usecase.bill_package

import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import javax.inject.Inject

class UpdatePackageNameUseCase @Inject constructor(
    private val repository: BillPackageRepository
) {

    suspend operator fun invoke(packageName: String, packageId: Long) {
        return repository.updatePackageName(packageName, packageId)
    }
}