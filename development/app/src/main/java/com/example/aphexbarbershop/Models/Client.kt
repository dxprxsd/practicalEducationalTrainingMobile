package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("client")
data class Client(
    @SerialName("id") val id: Int? = 0,
    @SerialName("uid") val uid: String, // Идентификатор пользователя (внешний ключ к auth.users)
    @SerialName("name_client") val name: String, // Имя
    @SerialName("surname_client") val surname: String, // Фамилия
    @SerialName("patronymic_client") val patronymic: String, // Отчество
    @SerialName("gender") val gender: Int, // Ссылка на gender
    @SerialName("phone_number") val phoneNumber: String?, // Телефон
    @SerialName("visit_count") val visitCount: Int = 0, // Количество визитов
    @SerialName("status_id") val statusId: Int? // Ссылка на clientStatus
)