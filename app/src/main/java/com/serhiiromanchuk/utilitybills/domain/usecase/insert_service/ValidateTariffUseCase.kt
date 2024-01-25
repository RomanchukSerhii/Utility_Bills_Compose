package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import javax.inject.Inject

class ValidateTariffUseCase @Inject constructor() {

    operator fun invoke(tariff: String): ValidationResult {
        if (tariff.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Вкажіть тариф для послуги"
            )
        }
        return ValidationResult(successful = true)
    }
}