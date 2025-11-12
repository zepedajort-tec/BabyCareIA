package com.app.babycare.domain.model

data class Baby(
    val id: Int,
    val parentId: Int,
    val name: String,
    val ageMonths: Int,
    val sex: BabySex,
    val weight: Float? = null,
    val height: Float? = null,
    val createdAt: String? = null
)

enum class BabySex(val value: String) {
    MALE("M"),
    FEMALE("F"),
    OTHER("O");

    companion object {
        // Ahora acepta String? y maneja null/blank devolviendo OTHER por defecto
        fun fromString(value: String?): BabySex {
            if (value == null) return OTHER
            return when (value.uppercase()) {
                "M" -> MALE
                "F" -> FEMALE
                "O" -> OTHER
                else -> OTHER
            }
        }
    }
}