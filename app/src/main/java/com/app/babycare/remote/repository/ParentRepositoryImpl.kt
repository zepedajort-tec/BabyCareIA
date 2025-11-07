package com.app.babycare.remote.repository

import com.app.babycare.domain.model.Parent
import com.app.babycare.domain.repository.ParentRepository
import com.app.babycare.remote.api.ParentApi
import com.app.babycare.remote.dto.UpdateParentDto

class ParentRepositoryImpl (
    private val api: ParentApi
): ParentRepository
{

    override suspend fun getAllParents(): Result<List<Parent>> {
        return try {
            val response = api.getAllParents()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * CORRECCIÓN: Esta función ahora llama a getParentByEmail
     */
    override suspend fun getParent(email: String): Result<Parent> {
        return try {
            val response = api.getParentByEmail(email)
            Result.success(response)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getParentById(parentId: Int): Result<Parent> {
        return try {
            val response = api.getParentById(parentId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateParent(parentId: Int, parent: UpdateParentDto): Result<Parent> {
        return try {
            val response = api.updateParent(parentId, parent)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteParent(parentId: Int): Result<Unit> {
        return try {
            val response = api.deleteParent(parentId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}