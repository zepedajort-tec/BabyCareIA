package com.app.babycare.remote.dto

data class UpdateRecordDto(
    val baby_id: Int,
    val date: String,
    val vaccine: String,
    val notes: String? = null
)