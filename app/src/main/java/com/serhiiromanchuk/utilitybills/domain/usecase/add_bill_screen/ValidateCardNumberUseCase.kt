package com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.usecase.ValidationResult
import com.serhiiromanchuk.utilitybills.utils.UiText
import javax.inject.Inject

class ValidateCardNumberUseCase @Inject constructor() {

    operator fun invoke(cardNumber: String): ValidationResult {
        if (cardNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_card_number_field_error)
            )
        }
        if (cardNumber.length < 16) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.not_enough_card_number_digits_error)
            )
        }
        return ValidationResult(true)
    }
}