package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.domain.model.HealthRecord
import com.app.babycare.domain.repository.DevTipsRepository
import com.app.babycare.domain.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TipUi(
    val category: String = "",
    val tipText: String = ""
)

data class HomeUiState(
    val tips: List<TipUi> = emptyList(),
    val tipOfDay: TipUi = TipUi(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // Health records UI state
    val healthRecords: List<HealthRecord> = emptyList(),
    val recordsLoading: Boolean = false,
    val recordsError: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val devTipsRepository: DevTipsRepository,
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        getDevTips()
        getHealthRecords()
    }

    fun getDevTips() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            devTipsRepository.getAllDevTips().fold(
                onSuccess = { devTips ->
                    // Mapear usando los campos esperados (category, description)
                    val mapped = devTips.map { devTip ->
                        TipUi(
                            category = devTip.category,
                            tipText = devTip.description
                        )
                    }

                    _uiState.update {
                        it.copy(
                            tips = mapped,
                            tipOfDay = mapped.firstOrNull() ?: TipUi(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al recuperar tips"
                        )
                    }
                }
            )
        }
    }

    fun getHealthRecords() {
        viewModelScope.launch {
            _uiState.update { it.copy(recordsLoading = true, recordsError = null) }

            healthRepository.getAllRecords().fold(
                onSuccess = { records ->
                    _uiState.update {
                        it.copy(
                            healthRecords = records,
                            recordsLoading = false,
                            recordsError = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            recordsLoading = false,
                            recordsError = error.message ?: "Error al recuperar registros"
                        )
                    }
                }
            )
        }
    }
}