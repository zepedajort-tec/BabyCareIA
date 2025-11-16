package com.app.babycare.domain.model

data class HealthRecord(
    val id: Int,
    val baby_id: Int,
    val date: String,
    val vaccine: String,
    val notes: String? = null
)