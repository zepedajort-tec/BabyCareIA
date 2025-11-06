package com.app.babycare.domain.repository

import com.app.babycare.domain.model.AuthResult
import com.app.babycare.domain.model.LoginCredentials
import com.app.babycare.domain.model.RegisterData

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): Result<AuthResult>
    suspend fun register(data: RegisterData): Result<AuthResult>
}