package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.domain.model.LoginCredentials
import com.app.babycare.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class LoginEvent {
    object NavigateToHome : LoginEvent()
    object NavigateToRegister : LoginEvent()
    data class ShowMessage(val message: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val credentials = LoginCredentials(email = email.trim(), password = password)
            repo.login(credentials).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(LoginEvent.NavigateToHome)
                            },
                onFailure = {
                    val msg = it.message ?: "Error de red"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(LoginEvent.ShowMessage(msg))
                }
            )
        }
    }

    fun navigateToRegister() {
        viewModelScope.launch { _events.emit(LoginEvent.NavigateToRegister) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
