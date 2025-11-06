package com.app.babycareia.domain.model

data class Parent(
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val relation: String? = null,
    val age: Int? = null,
    val sex: String = "O"
)