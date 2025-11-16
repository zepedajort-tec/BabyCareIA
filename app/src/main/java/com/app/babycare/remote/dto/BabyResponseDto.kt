package com.app.babycare.remote.dto

import com.google.gson.annotations.SerializedName

data class BabyResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("parent_id")
    val parentId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("age_months")
    val ageMonths: Int,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("weight")
    val weight: Float? = null,

    @SerializedName("height")
    val height: Float? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)