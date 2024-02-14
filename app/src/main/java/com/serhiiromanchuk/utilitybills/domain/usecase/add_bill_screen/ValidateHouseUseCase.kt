package com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.usecase.ValidationResult
import com.serhiiromanchuk.utilitybills.utils.UiText
import javax.inject.Inject

class ValidateHouseUseCase @Inject constructor() {

    operator fun invoke(house: String): ValidationResult {
        if (house.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_house_field_error)
            )
        }
        return ValidationResult(true)
    }
}