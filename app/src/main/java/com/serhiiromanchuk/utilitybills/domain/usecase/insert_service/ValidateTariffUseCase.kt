package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.usecase.ValidationResult
import com.serhiiromanchuk.utilitybills.utils.UiText
import com.serhiiromanchuk.utilitybills.utils.removeCurrencySign
import javax.inject.Inject

class ValidateTariffUseCase @Inject constructor() {

    operator fun invoke(tariff: String): ValidationResult {
        if (tariff.removeCurrencySign().isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.enter_tariff_for_service)
            )
        }
        return ValidationResult(successful = true)
    }
}