package com.serhiiromanchuk.utilitybills.domain.usecase.insert_service

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
