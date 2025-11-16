package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.domain.repository.DevTipsRepository
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
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val devTipsRepository: DevTipsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        getDevTips()
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
}