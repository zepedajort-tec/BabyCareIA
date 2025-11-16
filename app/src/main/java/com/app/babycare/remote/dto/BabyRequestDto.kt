package com.app.babycare.remote.dto

import com.google.gson.annotations.SerializedName

data class BabyRequestDto(
    @SerializedName("parent_id")
    val parentId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("age_months")
    val ageMonths: Int,

    @SerializedName("sex")
    val sex: String, // "M", "F", "O"

    @SerializedName("weight")
    val weight: Float? = null,

    @SerializedName("height")
    val height: Float? = null
)