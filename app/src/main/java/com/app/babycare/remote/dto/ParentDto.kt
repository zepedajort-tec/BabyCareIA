package com.app.babycare.remote.dto

data class UpdateParentDto(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null,
    val relation: String? = null,
    val age: Int? = null
)
