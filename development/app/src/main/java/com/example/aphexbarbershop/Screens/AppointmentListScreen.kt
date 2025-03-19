package com.example.aphexbarbershop.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val haircuts by viewModel.haircutsss.collectAsState() // Получаем список стрижек

    val allOrders = customOrdersItems.mapNotNull { order ->
        order.id?.let { id ->  // Проверяем, что id не null
            OrderItem(
                id = id, // Теперь id точно Int
                haircutName = haircuts[order.haircutId] ?: "Неизвестная стрижка",
                price = order.finalPrice,
                date = order.appointmentDate ?: "Дата не указана"
            )
        }
    }.sortedByDescending { it.date }

    LaunchedEffect(Unit) {
        viewModel.selectInfoAboutUserAppointments()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF565656))
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()), // Padding from status bar, // Темный фон
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("main_screen") }) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp),
                )
            }
            Text(
                text = "История заказов",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.logomini),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(), bottom = 16.dp), // Adding top padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "История заказов",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allOrders) { order ->
                        OrderCard(
                            id = order.id, // Передаем ID
                            haircutName = order.haircutName,
                            price = order.price,
                            date = formatDate(order.date),
                            onDeleteClick = { viewModel.deleteAppointmentFunction(order.id) }
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


data class OrderItem(
    val id: Int, // Добавлен ID заказа
    val haircutName: String,
    val price: Float,
    val date: String
)

@Composable
fun OrderCard(id: Int, haircutName: String, price: Float, date: String, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF99D77D))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Прическа: $haircutName", fontSize = 16.sp, color = Color.White)
                Text(text = "Цена: $price ₽", fontSize = 16.sp, color = Color.White)
                Text(text = "Дата стрижки: $date", fontSize = 16.sp, color = Color.White)
            }

            // Кнопка удаления
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF99D77D))
                    .clickable { onDeleteClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.deleteicon),
                    contentDescription = "Delete",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Функция для форматирования даты (удаление времени)
fun formatDate(dateTime: String?): String {
    return dateTime?.split("T")?.firstOrNull() ?: "Дата не указана"
}