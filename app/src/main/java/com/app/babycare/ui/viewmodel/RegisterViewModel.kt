package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.model.RegisterData
import com.app.babycare.domain.repository.AuthRepository
import com.app.babycare.domain.repository.ParentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(val isLoading: Boolean = false, val error: String? = null)

sealed class RegisterEvent {
    object NavigateToAddBaby : RegisterEvent()
    object NavigateToLogin : RegisterEvent()
    data class ShowMessage(val message: String) : RegisterEvent()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val parentRepository: ParentRepository,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvent>(replay = 0)
    val events = _events.asSharedFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        phone: String? = null,
        relation: String? = null,
        age: Int? = null
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val data = RegisterData(
                name = name.trim(),
                email = email.trim(),
                password = password,
                phone = phone,
                relation = relation,
                age = age
            )
            repo.register(data).fold(
                onSuccess = {
                    // Limpiar sesión previa antes de guardar la nueva
                    tokenStore.clearSession()
                    tokenStore.saveToken(it.token)

                    // Obtener el parent creado (por email) y guardar su info para usar parent_id
                    fetchAndSaveParentThenNavigate(email)
                },
                onFailure = {
                    val msg = it.message ?: "Error al registrarse"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(RegisterEvent.ShowMessage(msg))
                }
            )
        }
    }

    private fun fetchAndSaveParentThenNavigate(email: String) {
        viewModelScope.launch {
            parentRepository.getParent(email).fold(
                onSuccess = { parent ->
                    try {
                        tokenStore.saveUser(parent.id, parent.name, parent.email)
                    } catch (_: Exception) { /* ignore */ }
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(RegisterEvent.NavigateToAddBaby)
                },
                onFailure = {
                    val msg = it.message ?: "Registrado pero no se pudo obtener la info del usuario"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(RegisterEvent.ShowMessage(msg))
                    // Aún así podrías navegar a AddBaby si prefieres:
                    // _events.emit(RegisterEvent.NavigateToAddBaby)
                }
            )
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}