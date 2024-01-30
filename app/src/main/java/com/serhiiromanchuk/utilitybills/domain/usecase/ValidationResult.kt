package com.serhiiromanchuk.utilitybills.domain.usecase

import com.serhiiromanchuk.utilitybills.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
