package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import javax.inject.Inject

class ValidatePreviousValueUseCase @Inject constructor() {

    operator fun invoke(previousValue: String): ValidationResult {
        if (previousValue.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Вкажіть останні значення лічильника"
            )
        }
        return ValidationResult(successful = true)
    }
}