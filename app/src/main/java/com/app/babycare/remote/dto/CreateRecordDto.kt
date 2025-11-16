package com.app.babycare.remote.dto

data class CreateRecordDto(
    val baby_id: Int,
    val date: String,
    val vaccine: String,
    val notes: String? = null
)