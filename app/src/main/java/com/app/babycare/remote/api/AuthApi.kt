package com.app.babycare.remote.api

import com.app.babycare.remote.dto.LoginRequestDto
import com.app.babycare.remote.dto.RegisterRequestDto
import com.app.babycare.domain.model.AuthResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): AuthResult

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResult
}