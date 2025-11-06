package com.app.babycareia.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResult(
    @SerializedName("access_token")
    @Expose
    val token: String,
    @SerializedName("token_type")
    @Expose
    val tokenType: String
)