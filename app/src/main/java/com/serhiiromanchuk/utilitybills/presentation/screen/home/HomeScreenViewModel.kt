package com.serhiiromanchuk.utilitybills.presentation.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeUtilityServicesList
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(): ViewModel() {

    private val _screenState = mutableStateOf<HomeScreenState>(HomeScreenState.Initial)
    val screenState: HomeScreenState
        get() = _screenState.value

    private val bufferUtilityServicesList = mutableListOf<UtilityServiceItem>()

    init {
        fakeUtilityServicesList.forEach {
            bufferUtilityServicesList.add(it)
        }
        _screenState.value = HomeScreenState.Content(bufferUtilityServicesList)
    }
}