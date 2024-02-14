package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem

data class ChooseBillState (
    val billList: List<BillItem> = listOf()
)


