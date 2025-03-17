package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("haircuts_genders")
data class HaircutGender(
    @SerialName("id") val id: Int,
    @SerialName("hairgender_name") val hairgenderName: String // Пол стрижки
)