package com.app.babycareia.domain.repository

import com.app.babycareia.domain.model.AuthResult
import com.app.babycareia.domain.model.LoginCredentials
import com.app.babycareia.domain.model.RegisterData

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): Result<AuthResult>
    suspend fun register(data: RegisterData): Result<AuthResult>
}