package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("genders")
data class Gender(
    @SerialName("id") val id: Int,
    @SerialName("gender_name") val genderName: String // Пол
)