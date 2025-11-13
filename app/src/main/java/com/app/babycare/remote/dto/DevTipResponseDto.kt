package com.app.babycare.remote.dto

data class DevTipResponseDto(
    val id: Int,
    val age: Int,
    val category: String,
    val description: String,
    val createdAt: String? = null
)