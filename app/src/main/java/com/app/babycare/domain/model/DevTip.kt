package com.app.babycare.domain.model

data class DevTip(
    val id: Int,
    val age: Int,
    val category: String,
    val description: String,
    val createdAt: String? = null
)