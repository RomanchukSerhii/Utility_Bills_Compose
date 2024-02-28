package com.serhiiromanchuk.utilitybills.domain.usecase.bill_package

import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import javax.inject.Inject

class GetMaxIndexPositionUseCase @Inject constructor(
    private val repository: BillPackageRepository
){

    suspend operator fun invoke(): Int? {
        return repository.getMaxIndexPosition()
    }
}