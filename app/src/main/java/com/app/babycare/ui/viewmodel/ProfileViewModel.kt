package com.app.babycare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.domain.repository.ParentRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val parentRepository: ParentRepository,
    private val tokenStore: TokenStore
): ViewModel() {
    // crear una funcion que llame parentRepository suspend fun getParent(email: String): Result<Parent>
    fun getParent() {
        tokenStore.getUser()
    }
}