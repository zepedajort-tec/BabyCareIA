package com.app.babycare.domain.model

data class BabyData(
    val parentId: Int,
    val name: String,
    val ageMonths: Int,
    val sex: BabySex,
    val weight: Float? = null,
    val height: Float? = null
)