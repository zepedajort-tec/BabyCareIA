package com.app.babycare.remote.dto

data class ParentResponseDto(
    val user_id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val relation: String?,
    val age: Int?,
    val sex: String,
    val access_token: String
)