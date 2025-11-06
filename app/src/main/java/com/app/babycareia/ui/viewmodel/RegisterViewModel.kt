package com.app.babycareia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycareia.domain.model.RegisterData
import com.app.babycareia.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(val isLoading: Boolean = false, val error: String? = null)

sealed class RegisterEvent {
    object NavigateToLogin : RegisterEvent()
    data class ShowMessage(val message: String) : RegisterEvent()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvent>(replay = 0)
    val events = _events.asSharedFlow()

    fun register(name: String, email: String, password: String, phone: String? = null, relation: String? = null, age: Int? = null, sex: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val data = RegisterData(
                name = name.trim(),
                email = email.trim(),
                password = password,
                phone = phone,
                relation = relation,
                age = age,
                sex = sex ?: "O"
            )
            repo.register(data).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(RegisterEvent.NavigateToLogin)
                },
                onFailure = {
                    val msg = it.message ?: "Error al registrarse"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(RegisterEvent.ShowMessage(msg))
                }
            )
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
