package com.app.babycare.remote.dto


// Para PUT /parents/{parent_id}
// Basado en los campos requeridos y opcionales de tu API
data class UpdateParentDto(
    val name: String,
    val email: String,
    val password: String, // Tu API PUT requiere el hash
    val phone: String? = null,
    val relation: String? = null,
    val age: Int? = null,
    val sex: String? = "O"
)
