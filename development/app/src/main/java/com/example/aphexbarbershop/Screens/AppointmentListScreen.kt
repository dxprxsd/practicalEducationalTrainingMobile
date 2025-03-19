package com.example.aphexbarbershop.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.Models.Appointment
import com.example.aphexbarbershop.R

@Composable
fun AppointmentListScreen(navController: NavHostController, viewModel: MainViewModel) {

    val user by viewModel.usercur
    val customOrdersItems by viewModel.customOrders

    val allOrders = customOrdersItems.map { order ->
        OrderItem(
            haircutId = order.haircutId,
            price = order.finalPrice,
            date = order.appointmentDate ?: "Дата не указана"
        )
    }.sortedByDescending { it.date } // Сортируем по дате

    LaunchedEffect(Unit) {
        viewModel.selectInfoAboutUserAppointments()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF565656)), // Темный фон
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Распределяем пространство между элементами
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Логотип (левый)
            IconButton(onClick = { navController.navigate("main_screen") }) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = "Left Logo",
                    modifier = Modifier.size(40.dp), // Устанавливаем размер изображения
                )
            }
            Text(
                text = "История заказов",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // Логотип (правый)
            Image(
                painter = painterResource(id = R.drawable.logomini),
                contentDescription = "Right Logo",
                modifier = Modifier.size(70.dp) // Устанавливаем размер изображения
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Основной белый блок с полями авторизации
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "История заказов",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = false
                ) {
                    items(allOrders) { order ->
                        OrderCard(
                            haircutId = order.haircutId?.toString() ?: "Не указано",
                            price = order.price,
                            date = formatDate(order.date)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentScreenPreview() {
    val navController = rememberNavController()
    AppointmentListScreen(navController, MainViewModel())
}

// Универсальный класс для объединенного списка
data class OrderItem(
    val haircutId: Int?,
    val price: Float,
    val date: String
)

@Composable
fun OrderCard(haircutId: String, price: Float, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF99D77D))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Прическа: $haircutId",
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "Цена: $price ₽",
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "Дата стрижки: $date",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

// Функция для форматирования даты (удаление времени)
fun formatDate(dateTime: String?): String {
    return dateTime?.split("T")?.firstOrNull() ?: "Дата не указана"
}