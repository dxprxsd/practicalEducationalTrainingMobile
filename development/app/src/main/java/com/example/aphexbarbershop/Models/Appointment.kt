package com.example.aphexbarbershop.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("appointments")
data class Appointment(
    @SerialName("id") val id: Int,
    @SerialName("client_id") val clientId: Int, // Клиент
    @SerialName("employee_id") val employeeId: Int?, // Сотрудник
    @SerialName("haircut_id") val haircutId: Int?, // Стрижка
    @SerialName("appointment_date") val appointmentDate: String, // Дата записи
    @SerialName("final_price") val finalPrice: Float // Итоговая цена
)