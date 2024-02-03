package com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.usecase.ValidationResult
import com.serhiiromanchuk.utilitybills.utils.UiText
import javax.inject.Inject

class ValidateAddressUseCase @Inject constructor() {

    operator fun invoke(address: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_address_field_error)
            )
        }
        return ValidationResult(true)
    }
}
