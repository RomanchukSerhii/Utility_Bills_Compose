package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.utils.UiText
import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {

    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.enter_service_name)
            )
        }
        return ValidationResult(successful = true)
    }
}