package com.app.babycare.remote.api

import com.app.babycare.domain.model.Parent
import com.app.babycare.remote.dto.UpdateParentDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParentApi {
    @GET("parents")
    suspend fun getAllParents(): List<Parent>

    @GET("parent/{email}")
    suspend fun getParentByEmail(
        @Path("email")
        email: String
    ): Parent

    @GET("parents/{parent_id}")
    suspend fun getParentById(
        @Path("parent_id")
        parentId: Int
    ): Parent

    @PUT("parents/{parent_id}")
    suspend fun updateParent(
        @Path("parent_id") parentId: Int,
        @Body parent: UpdateParentDto
    ): Parent

    @DELETE("parents/{parent_id}")
    suspend fun deleteParent(
        @Path("parent_id")
        parentId: Int
    )
}