package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("roles")
data class Role(
    @SerialName("id") val id: Int,
    @SerialName("role_name") val roleName: String // Роль
)