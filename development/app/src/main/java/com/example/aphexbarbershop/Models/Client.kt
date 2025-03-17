package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("clients")
data class Client(
    @SerialName("id") val id: Int,
    @SerialName("name_client") val name: String, // Имя
    @SerialName("surname_client") val surname: String, // Фамилия
    @SerialName("patronymic_client") val patronymic: String, // Отчество
    @SerialName("gender") val gender: Int, // Ссылка на gender
    @SerialName("phone_number") val phoneNumber: String?, // Телефон
    @SerialName("visit_count") val visitCount: Int = 0, // Количество визитов
    @SerialName("status_id") val statusId: Int?, // Ссылка на clientStatus
    @SerialName("password") val password: String // Пароль
)