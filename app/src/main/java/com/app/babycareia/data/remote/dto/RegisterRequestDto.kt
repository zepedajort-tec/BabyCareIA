package com.app.babycareia.data.remote.dto

data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null,
    val relation: String? = null,
    val age: Int? = null,
    val sex: String = "O"
)