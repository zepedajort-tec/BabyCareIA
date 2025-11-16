package com.app.babycare.remote.api

import com.app.babycare.domain.model.HealthRecord
import com.app.babycare.remote.dto.CreateRecordDto
import com.app.babycare.remote.dto.UpdateRecordDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HealthApi {
    @GET("records")
    suspend fun getAllRecords(): List<HealthRecord>

    @GET("records/{record_id}")
    suspend fun getRecordById(
        @Path("record_id") recordId: Int
    ): HealthRecord

    @POST("records")
    suspend fun createRecord(
        @Body record: CreateRecordDto
    ): HealthRecord

    @PUT("records/{record_id}")
    suspend fun updateRecord(
        @Path("record_id") recordId: Int,
        @Body record: UpdateRecordDto
    ): HealthRecord

    @DELETE("records/{record_id}")
    suspend fun deleteRecord(
        @Path("record_id") recordId: Int
    )
}