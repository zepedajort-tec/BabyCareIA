package com.app.babycare.domain.model

data class Parent(
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val relation: String? = null,
    val age: Int? = null
)