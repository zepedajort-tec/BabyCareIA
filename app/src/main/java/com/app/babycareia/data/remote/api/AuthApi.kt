package com.app.babycareia.data.remote.api

import com.app.babycareia.data.remote.dto.LoginRequestDto
import com.app.babycareia.data.remote.dto.RegisterRequestDto
import com.app.babycareia.domain.model.AuthResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): AuthResult

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResult
}