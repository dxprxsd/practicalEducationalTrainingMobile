package com.example.aphexbarbershop.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.Models.Haircut
import com.example.aphexbarbershop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    // Observe the haircuts list
    val haircuts by viewModel.haircuts

    //var haircuties by remember { mutableStateOf<List<Haircut>>(listOf()) }

    //для поиска
    val searchQuery = viewModel.searchQuery
    val filteredHaircutss by viewModel.filteredHaircuts

    //для фильтрации по выбранному пункту
    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Все прически", "Мужские", "Женские")
    var selectedItem by remember { mutableStateOf("") }

    // Load haircuts when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.fetchHaircuts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF565656)), // Light background
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with logo and menu icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.aphexlogobarber),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(70.dp)
                    .width(160.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search field
        Row(
            modifier = Modifier
                .width(360.dp)
                .background(Color(0xFF99D77D), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Поисковое поле
            TextField(
                value = viewModel.searchQuery.value,
                onValueChange = { newText ->
                    viewModel.searchQuery.value = newText
                    viewModel.filteredHaircutMethod() // Применение фильтрации
                },
                placeholder = { Text(text = "Поиск") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp), // Отступ от кнопки фильтра
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFF99D77D), // Границы цвета 99D77D
                    focusedBorderColor = Color(0xFF99D77D)
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.Black) // Цвет текста черный
            )


            Spacer(modifier = Modifier.width(10.dp))

            // Кнопка для открытия меню
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF565656)), // Новый цвет
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp) // Убираем padding, если он был
            ) {
                Text(text = selectedItem)
            }

            // Меню для выбора типов
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(200.dp)
            ) {
                list.forEach { label ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = label,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        onClick = {
                            selectedItem = label
                            // В зависимости от выбранного типа аркана, кладем в view model соответствующий Id
                            when (selectedItem) {
                                "Все прически" -> {
                                    viewModel.typeOfReadyClothes = null
                                }
                                "Мужские" -> {
                                    viewModel.typeOfReadyClothes = 1
                                }
                                "Женские" -> {
                                    viewModel.typeOfReadyClothes = 2
                                }
                            }
                            // Вызываем метод для фильтрации карт
                            viewModel.filteredHaircutMethod()
                            expanded = false
                        }
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Discount information block
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(90.dp)
                .background(Color(0xFF99D77D), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Скидка",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color(0xFFD32F2F), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "скидка за 5 и более стрижек",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Обернем LazyVerticalGrid в Box для отображения индикатора загрузки
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(0xFFE8E8E8), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        ) {
            if (haircuts.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredHaircutss, key = { it.id }) { haircut ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .border(2.dp, Color(0xFF99D77D), RoundedCornerShape(12.dp)) // Граница карточки
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White) // Белый фон карточки
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val photoUrl = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(haircut.photo)
                                            .size(450, 450).build()
                                    ).state

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(160.dp)
                                            .border(
                                                2.dp,
                                                Color(0xFF99D77D),
                                                RoundedCornerShape(8.dp)
                                            ) // Обводка
                                            .padding(4.dp), // Отступ между границей и изображением
                                        contentAlignment = Alignment.Center // Центрируем изображение
                                    ) {
                                        if (photoUrl is AsyncImagePainter.State.Success) {
                                            Image(
                                                painter = photoUrl.painter!!,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxSize() // Уменьшаем высоту, чтобы учесть обводку
                                                    .clip(RoundedCornerShape(8.dp)), // Закругляем углы
                                                contentScale = ContentScale.Crop // Обрезает изображение, чтобы оно вписывалось
                                            )
                                        } else if (photoUrl is AsyncImagePainter.State.Loading) {
                                            CircularProgressIndicator(modifier = Modifier.size(40.dp))
                                        } else {
                                            Image(
                                                painter = painterResource(R.drawable.aphexlogobarber),
                                                contentDescription = "Fallback Image",
                                                modifier = Modifier.size(50.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = haircut.name, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "${haircut.price} ₽", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
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
                .padding(16.dp),
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
                onClick = { navController.navigate("ordering_on_haircut_screen") },
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
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController, MainViewModel())
}