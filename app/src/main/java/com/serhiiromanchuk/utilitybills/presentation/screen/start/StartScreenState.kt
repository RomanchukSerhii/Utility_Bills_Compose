package com.serhiiromanchuk.utilitybills.presentation.screen.start

import com.serhiiromanchuk.utilitybills.domain.model.BillItem

data class StartScreenState (
    val billList: List<BillItem> = listOf()
)


