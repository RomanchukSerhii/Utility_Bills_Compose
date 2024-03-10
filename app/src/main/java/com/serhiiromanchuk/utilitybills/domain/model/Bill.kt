package com.serhiiromanchuk.utilitybills.domain.model

import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID
import java.time.LocalDate

data class Bill(
    val id: Long = UNDEFINED_ID,
    val packageCreatorId: Long,
    val date: LocalDate,
    val billDescription: String? = null
)