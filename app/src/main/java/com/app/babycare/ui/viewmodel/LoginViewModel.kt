package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.model.LoginCredentials
import com.app.babycare.domain.repository.AuthRepository
import com.app.babycare.domain.repository.BabyRepository
import com.app.babycare.domain.repository.ParentRepository
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
    object None : LoginEvent()
    object NavigateToHome : LoginEvent()
    object NavigateToRegister : LoginEvent()
    object NavigateToRegisterBaby : LoginEvent()
    data class ShowMessage(val message: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val parentRepository: ParentRepository,
    private val babyRepository: BabyRepository,
    private val tokenStore: TokenStore
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
                    // Limpiar sesión previa antes de guardar la nueva
                    tokenStore.clearSession()
                    // Guardar token de forma segura
                    tokenStore.saveToken(it.token)
                    // Obtener info del parent (por email) y guardarla
                    getParentInfoAndProceed(email)
                },
                onFailure = {
                    val msg = it.message ?: "Error de red"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(LoginEvent.ShowMessage(msg))
                }
            )
        }
    }

    private fun getParentInfoAndProceed(email: String) {
        viewModelScope.launch {
            parentRepository.getParent(email).fold(
                onSuccess = { parent ->
                    // Guardar info del usuario actual para usar parent.id en otras operaciones
                    try {
                        tokenStore.saveUser(parent.id, parent.name, parent.email)
                    } catch (_: Exception) { /* no bloquear flujo si falla el guardado */ }

                    // Continuar con la lógica de bebés
                    getBabiesForUser(parent.id)
                },
                onFailure = {
                    val msg = it.message ?: "Error de red"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(LoginEvent.ShowMessage(msg))
                }
            )
        }
    }

    private fun getBabiesForUser(userId: Int) {
        viewModelScope.launch {
            babyRepository.getBabiesByParentId(userId).fold(
                onSuccess = { babies ->
                    _uiState.update { it.copy(isLoading = false) }
                    if (babies.isEmpty()) {
                        _events.emit(LoginEvent.NavigateToRegisterBaby)
                    } else {
                        _events.emit(LoginEvent.NavigateToHome)
                    }
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