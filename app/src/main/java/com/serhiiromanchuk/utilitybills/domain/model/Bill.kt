package com.serhiiromanchuk.utilitybills.domain.model

import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

data class Bill(
    val id: Long = UNDEFINED_ID,
    val packageCreatorId: Long,
    val date: String,
    val billDescription: String? = null
)