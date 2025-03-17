package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("employees")
data class Employee(
    @SerialName("id") val id: Int,
    @SerialName("name_employee") val name: String, // Имя
    @SerialName("surname_employee") val surname: String, // Фамилия
    @SerialName("patronymic_employee") val patronymic: String, // Отчество
    @SerialName("gender") val gender: Int, // Ссылка на gender
    @SerialName("role_id") val roleId: Int, // Роль
    @SerialName("contact_info") val contactInfo: String?, // Контактная информация
    @SerialName("password") val password: String // Пароль
)