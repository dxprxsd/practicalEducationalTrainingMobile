package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("client_status")
data class ClientStatus(
    @SerialName("id") val id: Int,
    @SerialName("status_name") val statusName: String? // Статус клиента
)