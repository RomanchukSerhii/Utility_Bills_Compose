package com.serhiiromanchuk.utilitybills.presentation.screen.home

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

sealed class HomeScreenState {

    data object Initial : HomeScreenState()

    data class Content(
        val list: List<UtilityServiceItem> = listOf()
    ) : HomeScreenState() {
        val isCreateBillEnabled: Boolean
            get() {
                list.forEach { utilityService ->
                    if (utilityService.isChecked) return true
                }
                return false
            }
    }
}