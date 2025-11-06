package com.app.babycareia.data.remote.repository

import com.app.babycareia.data.remote.api.AuthApi
import com.app.babycareia.data.remote.dto.LoginRequestDto
import com.app.babycareia.data.remote.dto.RegisterRequestDto
import com.app.babycareia.domain.model.AuthResult
import com.app.babycareia.domain.model.LoginCredentials
import com.app.babycareia.domain.model.RegisterData
import com.app.babycareia.domain.repository.AuthRepository

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