package com.app.babycare.domain.repository

import com.app.babycare.domain.model.Baby
import com.app.babycare.domain.model.BabyData

interface BabyRepository {
    suspend fun getAllBabies(): Result<List<Baby>>

    suspend fun getBabyById(babyId: Int): Result<Baby>

    suspend fun getBabiesByParentId(parentId: Int): Result<List<Baby>>

    suspend fun createBaby(babyData: BabyData): Result<Baby>

    suspend fun updateBaby(babyId: Int, babyData: BabyData): Result<Baby>

    suspend fun deleteBaby(babyId: Int): Result<String>
}