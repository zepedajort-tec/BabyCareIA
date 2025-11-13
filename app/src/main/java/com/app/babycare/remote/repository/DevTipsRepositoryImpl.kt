package com.app.babycare.remote.repository

import com.app.babycare.domain.model.DevTip
import com.app.babycare.domain.model.DevTipData
import com.app.babycare.domain.repository.DevTipsRepository
import com.app.babycare.remote.api.DevTipsApi
import com.app.babycare.remote.dto.DevTipRequestDto
import com.app.babycare.remote.dto.DevTipResponseDto
import javax.inject.Inject

class DevTipsRepositoryImpl @Inject constructor(
    private val api: DevTipsApi
) : DevTipsRepository {

    override suspend fun getAllDevTips(): Result<List<DevTip>> {
        return try {
            val response = api.getAllDevTips()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDevTipById(tipId: Int): Result<DevTip> {
        return try {
            val response = api.getDevTipById(tipId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createDevTip(devTipData: DevTipData): Result<String> {
        return try {
            val dto = DevTipRequestDto(
                age = devTipData.age,
                category = devTipData.category,
                description = devTipData.description
            )
            val response = api.createDevTip(dto)
            Result.success(response["message"] ?: "Development Tip created successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDevTip(tipId: Int, devTipData: DevTipData): Result<String> {
        return try {
            val dto = DevTipRequestDto(
                age = devTipData.age,
                category = devTipData.category,
                description = devTipData.description
            )
            val response = api.updateDevTip(tipId, dto)
            Result.success(response["message"] ?: "Development Tip updated successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDevTip(tipId: Int): Result<String> {
        return try {
            val response = api.deleteDevTip(tipId)
            Result.success(response["message"] ?: "Development Tip deleted successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Convierte el DTO de red a modelo de dominio
    private fun DevTipResponseDto.toDomain(): DevTip {
        return DevTip(
            id = this.id,
            age = this.age,
            category = this.category,
            description = this.description,
            createdAt = this.createdAt
        )
    }
}