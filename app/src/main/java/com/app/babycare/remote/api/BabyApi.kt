package com.app.babycare.remote.api

import com.app.babycare.remote.dto.BabyRequestDto
import com.app.babycare.remote.dto.BabyResponseDto
import retrofit2.http.*

interface BabyApi {
    @GET("babies")
    suspend fun getAllBabies(): List<BabyResponseDto>

    @GET("babies/{baby_id}")
    suspend fun getBabyById(@Path("baby_id") babyId: Int): BabyResponseDto

    @GET("parents/{parent_id}/babies")
    suspend fun getBabiesByParentId(@Path("parent_id") parentId: Int): List<BabyResponseDto>

    @POST("babies")
    suspend fun createBaby(@Body baby: BabyRequestDto): BabyResponseDto

    @PUT("babies/{baby_id}")
    suspend fun updateBaby(
        @Path("baby_id") babyId: Int,
        @Body baby: BabyRequestDto
    ): BabyResponseDto

    @DELETE("babies/{baby_id}")
    suspend fun deleteBaby(@Path("baby_id") babyId: Int): Map<String, String>
}