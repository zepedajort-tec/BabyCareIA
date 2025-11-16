package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.model.Parent
import com.app.babycare.domain.repository.ParentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val parent: Parent? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val parentRepository: ParentRepository,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadParent()
    }

    private fun loadParent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Obtener el email del usuario actual desde TokenStore
            val currentUser = tokenStore.getUser()

            if (currentUser == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No se encontró información del usuario. Por favor inicia sesión de nuevo."
                    )
                }
                return@launch
            }

            // Llamar al repository para obtener los datos actualizados del padre
            parentRepository.getParent(currentUser.email).fold(
                onSuccess = { parent ->
                    _uiState.update {
                        it.copy(
                            parent = parent,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al cargar el perfil"
                        )
                    }
                }
            )
        }
    }

    fun refresh() {
        loadParent()
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}