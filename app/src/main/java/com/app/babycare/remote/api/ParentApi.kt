package com.app.babycare.remote.api

import com.app.babycare.domain.model.Parent
import com.app.babycare.remote.dto.AuthResponse
import com.app.babycare.remote.dto.LoginRequestDto
import com.app.babycare.remote.dto.RegisterRequestDto
import com.app.babycare.remote.dto.UpdateParentDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Header

interface ParentApi {

    // --- Operaciones CRUD de Parent ---

    @GET("parents")
    suspend fun getAllParents(): List<Parent>

    /**
     * CORRECCIÓN: Se usa @Path para /{email} y la ruta es /parent/{email}
     * FastAPI: @app.get("/parent/{email}")
     */
    @GET("parent/{email}")
    suspend fun getParentByEmail(@Path("email") email: String): Parent

    @GET("parents/{parent_id}")
    suspend fun getParentById(@Path("parent_id") parentId: Int): Parent

    @PUT("parents/{parent_id}")
    suspend fun updateParent(
        @Path("parent_id") parentId: Int,
        @Body parent: UpdateParentDto
    ): Parent // Asumiendo que la API devuelve el padre actualizado

    @DELETE("parents/{parent_id}")
    suspend fun deleteParent(@Path("parent_id") parentId: Int): Unit // Unit si no devuelve contenido (204)
}