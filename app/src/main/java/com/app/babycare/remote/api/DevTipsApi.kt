package com.app.babycare.remote.api

import com.app.babycare.remote.dto.DevTipRequestDto
import com.app.babycare.remote.dto.DevTipResponseDto
import retrofit2.http.*

interface DevTipsApi {
    @GET("devtips")
    suspend fun getAllDevTips(): List<DevTipResponseDto>

    @GET("devtips/{tip_id}")
    suspend fun getDevTipById(@Path("tip_id") tipId: Int): DevTipResponseDto

    @POST("devtips")
    suspend fun createDevTip(@Body tip: DevTipRequestDto): Map<String, String>

    @PUT("devtips/{tip_id}")
    suspend fun updateDevTip(
        @Path("tip_id") tipId: Int,
        @Body tip: DevTipRequestDto
    ): Map<String, String>

    @DELETE("devtips/{tip_id}")
    suspend fun deleteDevTip(@Path("tip_id") tipId: Int): Map<String, String>
}