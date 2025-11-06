package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableSharedFlow<SplashEvent>(replay = 0)
    val events = _events.asSharedFlow()

    fun startSplash(timeoutMs: Long = 1200L) {
        viewModelScope.launch {
            // aquí podrías comprobar token/auto-login con repository
            delay(timeoutMs)
            _events.emit(SplashEvent.NavigateToLogin)
        }
    }
}

sealed class SplashEvent {
    object NavigateToLogin : SplashEvent()
    object NavigateToHome : SplashEvent()
}
