package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("haircuts")
data class Haircut(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String, // Название стрижки
    @SerialName("gender") val gender: Int, // Пол стрижки
    @SerialName("price") val price: Float, // Цена
    @SerialName("photo") val photo: String? = null, // Фото
)