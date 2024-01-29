package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.utils.UiText
import javax.inject.Inject

class ValidatePreviousValueUseCase @Inject constructor() {

    operator fun invoke(previousValue: String): ValidationResult {
        if (previousValue.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.enter_previous_meter_value)
            )
        }
        return ValidationResult(successful = true)
    }
}