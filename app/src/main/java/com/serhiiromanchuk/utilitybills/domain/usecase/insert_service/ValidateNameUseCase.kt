package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {

    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Вкажіть ім'я послуги"
            )
        }
        return ValidationResult(successful = true)
    }
}