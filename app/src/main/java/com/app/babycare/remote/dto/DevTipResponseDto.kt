package com.app.babycare.remote.dto

data class DevTipResponseDto(
    val id: Int,
    val age_range: Int,
    val category: String,
    val tip_text: String,
    val createdAt: String? = null
)