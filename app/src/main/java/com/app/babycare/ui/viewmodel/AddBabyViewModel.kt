package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.model.BabyData
import com.app.babycare.domain.model.BabySex
import com.app.babycare.domain.repository.BabyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddBabyUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class AddBabyEvent {
    object NavigateToHome : AddBabyEvent()
    data class ShowMessage(val message: String) : AddBabyEvent()
}

@HiltViewModel
class AddBabyViewModel @Inject constructor(
    private val babyRepository: BabyRepository,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBabyUiState())
    val uiState: StateFlow<AddBabyUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddBabyEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    // Exponer el parentId actual (puede ser null si no hay sesión)
    private val _currentParentId = MutableStateFlow<Int?>(tokenStore.getUser()?.id)
    val currentParentId: StateFlow<Int?> = _currentParentId.asStateFlow()

    // Permite refrescar manualmente el parentId (si cambió la sesión)
    fun refreshCurrentParent() {
        _currentParentId.value = tokenStore.getUser()?.id
    }

    fun saveBaby(
        parentId: Int? = null, // ahora opcional; si es null usamos el parentId guardado
        name: String,
        ageMonths: Int,
        sex: BabySex,
        weight: Float?,
        height: Float?
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val finalParentId = parentId ?: _currentParentId.value
            if (finalParentId == null) {
                _uiState.update { it.copy(isLoading = false, error = "No se encontró el padre actual. Inicia sesión de nuevo.") }
                _events.emit(AddBabyEvent.ShowMessage("No se encontró el padre actual. Inicia sesión de nuevo."))
                return@launch
            }

            val babyData = BabyData(
                parentId = finalParentId,
                name = name,
                ageMonths = ageMonths,
                sex = sex,
                weight = weight,
                height = height
            )

            babyRepository.createBaby(babyData).fold(
                onSuccess = { baby ->
                    _uiState.update { it.copy(isLoading = false, success = true) }
                    _events.emit(AddBabyEvent.ShowMessage("Bebé registrado exitosamente"))
                    _events.emit(AddBabyEvent.NavigateToHome)
                },
                onFailure = { error ->
                    val msg = error.message ?: "Error al registrar el bebé"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    _events.emit(AddBabyEvent.ShowMessage(msg))
                }
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}