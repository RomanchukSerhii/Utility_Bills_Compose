package com.serhiiromanchuk.utilitybills.domain.model

import com.serhiiromanchuk.utilitybills.utils.INITIAL_INDEX_POSITION
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

data class Bill(
    val id: Long = UNDEFINED_ID,
    val packageCreatorId: Long,
    val date: String,
    val billDescription: String = "",
    val indexPosition: Int = INITIAL_INDEX_POSITION
)