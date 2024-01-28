package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import com.serhiiromanchuk.utilitybills.utils.removeCurrencySign
import javax.inject.Inject

class ValidateTariffUseCase @Inject constructor() {

    operator fun invoke(tariff: String): ValidationResult {
        if (tariff.removeCurrencySign().isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Вкажіть тариф для послуги"
            )
        }
        return ValidationResult(successful = true)
    }
}