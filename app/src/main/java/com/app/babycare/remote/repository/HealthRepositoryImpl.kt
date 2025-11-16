package com.app.babycare.remote.repository

import com.app.babycare.domain.model.HealthRecord
import com.app.babycare.domain.repository.HealthRepository
import com.app.babycare.remote.api.HealthApi
import com.app.babycare.remote.dto.CreateRecordDto
import com.app.babycare.remote.dto.UpdateRecordDto
import javax.inject.Inject

class HealthRepositoryImpl @Inject constructor(
    private val api: HealthApi
) : HealthRepository {

    override suspend fun getAllRecords(): Result<List<HealthRecord>> {
        return try {
            val response = api.getAllRecords()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecord(recordId: Int): Result<HealthRecord> {
        return try {
            val response = api.getRecordById(recordId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createRecord(record: CreateRecordDto): Result<HealthRecord> {
        return try {
            val response = api.createRecord(record)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateRecord(recordId: Int, record: UpdateRecordDto): Result<HealthRecord> {
        return try {
            val response = api.updateRecord(recordId, record)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteRecord(recordId: Int): Result<Unit> {
        return try {
            val response = api.deleteRecord(recordId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}