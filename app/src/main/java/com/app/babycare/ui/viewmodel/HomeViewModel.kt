package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.repository.DevTipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val devTipsRepository: DevTipsRepository
): ViewModel() {

    /**
     * Compartir en copilot (HomeViewModel.kt, HomeScreen.kt, AppaNavGraph.kt, EncryptedTokenStore.kt)
     *
     * Completame el codigo del HomeViewModel para que actualice el HomeScreen con una lista de consejos de desarrollo
     * como titulo por la categoria y como descripcion el tip_text
     */
    fun getDevTips() {
        viewModelScope.launch {
            devTipsRepository.getAllDevTips().fold(
                onSuccess = { devTips ->
                    val devtip = devTips.first()
                    // Aquí puedes actualizar el estado de la UI con la lista de consejos de desarrollo
                    // Por ejemplo, podrías usar un StateFlow o LiveData para exponer los datos al HomeScreen
                },
                onFailure = { error ->
                    // Manejar el error, por ejemplo, mostrando un mensaje en la UI
                }
            )
        }
    }
}