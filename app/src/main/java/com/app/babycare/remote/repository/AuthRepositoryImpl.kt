package com.app.babycare.remote.repository

import com.app.babycare.remote.api.AuthApi
import com.app.babycare.remote.dto.LoginRequestDto
import com.app.babycare.remote.dto.RegisterRequestDto
import com.app.babycare.domain.model.AuthResult
import com.app.babycare.domain.model.LoginCredentials
import com.app.babycare.domain.model.RegisterData
import com.app.babycare.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(credentials: LoginCredentials): Result<AuthResult> {
        return try {
            val dto = LoginRequestDto(credentials.email, credentials.password)
            val response = api.login(dto)

            Result.success(response)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(data: RegisterData): Result<AuthResult> {
        return try {
            val dto = RegisterRequestDto(
                name = data.name,
                email = data.email,
                password = data.password,
                phone = data.phone,
                relation = data.relation,
                age = data.age,
                sex = data.sex
            )
            val response = api.register(dto)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}