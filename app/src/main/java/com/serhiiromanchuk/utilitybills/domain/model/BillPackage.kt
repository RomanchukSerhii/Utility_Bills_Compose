package com.serhiiromanchuk.utilitybills.domain.model

import com.serhiiromanchuk.utilitybills.utils.INITIAL_INDEX_POSITION
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

data class BillPackage(
    val id: Long = UNDEFINED_ID,
    val name: String,
    val payerName: String,
    val address: String,
    val indexPosition: Int = INITIAL_INDEX_POSITION
)
