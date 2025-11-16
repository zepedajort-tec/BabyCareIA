package com.app.babycare.domain.repository

import com.app.babycare.domain.model.Parent
import com.app.babycare.remote.dto.UpdateParentDto

interface ParentRepository {
    suspend fun getAllParents(): Result<List<Parent>>
    suspend fun getParent(email: String): Result<Parent>
    suspend fun getParentById(parentId: Int): Result<Parent>
    suspend fun updateParent(parentId: Int, parent: UpdateParentDto): Result<Parent>
    suspend fun deleteParent(parentId: Int): Result<Unit>
}