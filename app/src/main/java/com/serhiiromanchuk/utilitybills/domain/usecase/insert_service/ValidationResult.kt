package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

import com.serhiiromanchuk.utilitybills.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
