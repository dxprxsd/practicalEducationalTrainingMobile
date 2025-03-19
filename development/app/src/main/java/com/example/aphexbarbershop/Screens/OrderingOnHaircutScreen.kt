package com.example.aphexbarbershop.Screens

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.R
import java.util.Calendar

@Composable
fun OrderingOnHaircutScreen(navController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current

    val listHaircuts by viewModel.haircutstypes.collectAsState(initial = emptyList())

    val listEmployee by viewModel.emploeesstypes.collectAsState(initial = emptyList()) // List of employees

    LaunchedEffect(Unit) {
        viewModel.selectProfile()
        //MainViewModel.fetchCurrentUser()
        viewModel.getTypeHaircut()
        viewModel.getTypeEmploee()
    }

    // Подписываемся на StateFlow
    //val users by viewModel.user.collectAsState()
    //Log.d("my_tag", "User data: $users")

    //val iduser by remember { mutableStateOf(users.lastOrNull()?.id ?: 0) }

    var haircuttype by remember { mutableStateOf(0) } // Начальное значение - 1
    var emploeetype by remember { mutableStateOf(0) } // Начальное значение - 1
    var haircutemploee by remember { mutableStateOf("") }
    var dateOfHaircut by remember { mutableStateOf("") }

    var expandedHaircut by remember { mutableStateOf(false) } // Состояние выпадающего списка для стрижки
    var expandedEmploee by remember { mutableStateOf(false) } // Состояние выпадающего списка для парикмахера

    val selectedHaircut = listHaircuts.find { it.id == haircuttype }
    val selectedHaircutText =
        listHaircuts.find { it.id == haircuttype }?.name ?: "Выберите прическу"
    val selectedEmploeeText =
        listEmployee.find { it.id == emploeetype }?.name ?: "Выберите парикмахера"
    val selectedHaircutPrice = selectedHaircut?.price ?: 0f

    // Checking if the user has enough visits for a discount
    // Также добавьте проверку на null
    //val currentUser = iduser
//    if (currentUser == null) {
//        Log.e("my_tag", "Пользователь не найден!")
//    } else {
//        Log.d("my_tag", "Пользователь загружен: $currentUser")
//    }
    val hasDiscount = true
    val discountedPrice = if (hasDiscount) {
        selectedHaircutPrice * 0.97 // Применяем скидку 3%
    } else {
        selectedHaircutPrice // Без скидки
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate =
                "$year-${month + 1}-$dayOfMonth" // Формат YYYY-MM-DD для PostgreSQL
            dateOfHaircut = selectedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF565656)) // Темный фон
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Добавлен отступ снизу для навигационной панели
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Запись на стрижку",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.logomini),
                    contentDescription = "Right Logo",
                    modifier = Modifier.size(70.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0xFFE8E8E8),
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start // Выровнять по левому краю
                    ) {
                        Text(
                            text = "Прическа",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Left
                        )
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedHaircutText,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(2.dp, Color(0xFF99D77D)),
                                    RoundedCornerShape(10.dp)
                                )
                                .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                                .clickable { expandedHaircut = true },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            readOnly = true
                        )
                        // Кнопка для открытия выпадающего списка
                        IconButton(
                            onClick = {
                                expandedHaircut = !expandedHaircut
                                viewModel.getTypeHaircut()
                            }, // Переключаем состояние expanded
                            modifier = Modifier.align(Alignment.CenterEnd) // Размещаем кнопку справа
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.backbutton), // Используем свою кнопку
                                contentDescription = "Back Button",
                                modifier = Modifier.size(24.dp) // Размер кнопки
                            )
                        }

                        DropdownMenu(
                            expanded = expandedHaircut,
                            onDismissRequest = { expandedHaircut = false }) {
                            listHaircuts.forEach { haircut ->
                                DropdownMenuItem(
                                    text = { Text(text = haircut.name) },
                                    onClick = {
                                        haircuttype = haircut.id
                                        expandedHaircut = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start // Выровнять по левому краю
                    ) {
                        Text(
                            text = "Парикмахер",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Left
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedEmploeeText,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(2.dp, Color(0xFF99D77D)),
                                    RoundedCornerShape(10.dp)
                                )
                                .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                                .clickable { expandedEmploee = true },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            readOnly = true
                        )
                        // Кнопка для открытия выпадающего списка
                        IconButton(
                            onClick = {
                                expandedEmploee = !expandedEmploee
                                viewModel.getTypeEmploee()
                            }, // Переключаем состояние expanded
                            modifier = Modifier.align(Alignment.CenterEnd) // Размещаем кнопку справа
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.backbutton), // Используем свою кнопку
                                contentDescription = "Back Button",
                                modifier = Modifier.size(24.dp) // Размер кнопки
                            )
                        }

                        DropdownMenu(
                            expanded = expandedEmploee,
                            onDismissRequest = { expandedEmploee = false }) {
                            listEmployee.forEach { employee ->
                                DropdownMenuItem(
                                    text = { Text(text = employee.name) },
                                    onClick = {
                                        emploeetype = employee.id
                                        expandedEmploee = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start // Выровнять по левому краю
                    ) {
                        Text(
                            text = "Дата",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Left
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = dateOfHaircut,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(2.dp, Color(0xFF99D77D)),
                                    RoundedCornerShape(10.dp)
                                )
                                .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                                .clickable { datePickerDialog.show() },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            readOnly = true
                        )
                        // Кнопка для открытия выпадающего списка
                        IconButton(
                            onClick = { datePickerDialog.show() }, // Переключаем состояние expanded
                            modifier = Modifier.align(Alignment.CenterEnd) // Размещаем кнопку справа
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.backbutton), // Используем свою кнопку
                                contentDescription = "Back Button",
                                modifier = Modifier.size(24.dp) // Размер кнопки
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // Display the price with a discount if applicable
                    if (selectedHaircut != null) {
                        Column {
                            if (hasDiscount) {
                                // Отображаем оригинальную цену с зачеркиванием, если скидка применима
                                Text(
                                    text = "Цена: ${selectedHaircutPrice}₽", // оригинальная цена
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                )
                            }

                            // Отображаем цену со скидкой
                            Text(
                                text = "Цена со скидкой: ${discountedPrice}₽", // цена со скидкой
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    // Кнопка "Продолжить"
                    Button(
                        onClick = {
                            viewModel.addNewAppointment(
                                employeeId = emploeetype, // Выбранный парикмахер
                                haircutId = haircuttype, // Выбранная стрижка
                                appointmentDate = dateOfHaircut, // Выбранная дата
                                finalPrice = selectedHaircutPrice // Итоговая цена
                            )
                            Toast.makeText(
                                context,
                                "Запись успешно добавлена",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98E58C)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Готово",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

        // Navigation panel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                )
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { navController.navigate("main_screen") },
                modifier = Modifier
                    .size(64.dp) // Увеличение размера кнопки
                    .background(
                        color = Color(0xFF99D77D),
                        shape = RoundedCornerShape(10.dp) // Закругление углов
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Go to Main",
                        tint = Color(0xFFFFFFFF), // Цвет иконки
                        modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                    )
                }
            }
            IconButton(
                onClick = {
                    Toast.makeText(context, "Вы уже оформляете заказ!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .size(64.dp) // Увеличение размера кнопки
                    .background(
                        color = Color(0xFF99D77D),
                        shape = RoundedCornerShape(10.dp) // Закругление углов
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Go to Main",
                        tint = Color(0xFFFFFFFF), // Цвет иконки
                        modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                    )
                }
            }
            IconButton(
                onClick = { navController.navigate("user_profile_screen") },
                modifier = Modifier
                    .size(64.dp) // Увеличение размера кнопки
                    .background(
                        color = Color(0xFF99D77D),
                        shape = RoundedCornerShape(10.dp) // Закругление углов
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.userprofile),
                        contentDescription = "Go to Main",
                        tint = Color(0xFFFFFFFF), // Цвет иконки
                        modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegistrationOnHaircutScreenPreview() {
    val navController = rememberNavController()
    OrderingOnHaircutScreen(navController, MainViewModel())
}