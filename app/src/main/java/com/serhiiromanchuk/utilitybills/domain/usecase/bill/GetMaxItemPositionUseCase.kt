package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class GetMaxItemPositionUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(): Int? {
        return repository.getMaxItemPosition()
    }
}