package com.app.babycare.remote.repository

import com.app.babycare.remote.api.BabyApi
import com.app.babycare.remote.dto.BabyRequestDto
import com.app.babycare.remote.dto.BabyResponseDto
import com.app.babycare.domain.model.Baby
import com.app.babycare.domain.model.BabyData
import com.app.babycare.domain.model.BabySex
import com.app.babycare.domain.repository.BabyRepository
import javax.inject.Inject

class BabyRepositoryImpl @Inject constructor(
    private val api: BabyApi
) : BabyRepository {

    override suspend fun getAllBabies(): Result<List<Baby>> {
        return try {
            val response = api.getAllBabies()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBabyById(babyId: Int): Result<Baby> {
        return try {
            val response = api.getBabyById(babyId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBabiesByParentId(parentId: Int): Result<List<Baby>> {
        return try {
            val response = api.getBabiesByParentId(parentId)
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createBaby(babyData: BabyData): Result<Baby> {
        return try {
            val dto = BabyRequestDto(
                parentId = babyData.parentId,
                name = babyData.name,
                ageMonths = babyData.ageMonths,
                sex = babyData.sex.value,
                weight = babyData.weight,
                height = babyData.height
            )
            val response = api.createBaby(dto)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBaby(babyId: Int, babyData: BabyData): Result<Baby> {
        return try {
            val dto = BabyRequestDto(
                parentId = babyData.parentId,
                name = babyData.name,
                ageMonths = babyData.ageMonths,
                sex = babyData.sex.value,
                weight = babyData.weight,
                height = babyData.height
            )
            val response = api.updateBaby(babyId, dto)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBaby(babyId: Int): Result<String> {
        return try {
            val response = api.deleteBaby(babyId)
            Result.success(response["message"] ?: "Baby deleted successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Extension function para convertir DTO a Domain Model
    private fun BabyResponseDto.toDomain(): Baby {
        return Baby(
            id = this.id,
            parentId = this.parentId,
            name = this.name,
            ageMonths = this.ageMonths,
            sex = BabySex.fromString(this.sex),
            weight = this.weight,
            height = this.height,
            createdAt = this.createdAt
        )
    }
}