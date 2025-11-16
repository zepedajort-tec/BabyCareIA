package com.app.babycare.domain.repository

import com.app.babycare.domain.model.HealthRecord
import com.app.babycare.remote.dto.CreateRecordDto
import com.app.babycare.remote.dto.UpdateRecordDto

interface HealthRepository {
    suspend fun getAllRecords(): Result<List<HealthRecord>>
    suspend fun getRecord(recordId: Int): Result<HealthRecord>
    suspend fun createRecord(record: CreateRecordDto): Result<HealthRecord>
    suspend fun updateRecord(recordId: Int, record: UpdateRecordDto): Result<HealthRecord>
    suspend fun deleteRecord(recordId: Int): Result<Unit>
}