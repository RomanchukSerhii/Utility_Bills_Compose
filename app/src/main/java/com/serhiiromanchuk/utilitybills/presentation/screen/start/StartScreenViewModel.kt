package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _screenState = MutableStateFlow(StartScreenUiState())
    val screenState: StateFlow<StartScreenUiState> = _screenState.asStateFlow()
}