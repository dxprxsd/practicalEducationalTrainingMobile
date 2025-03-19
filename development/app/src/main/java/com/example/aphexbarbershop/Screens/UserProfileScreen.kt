package com.example.aphexbarbershop.Screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.R

@Composable
fun UserProfileScreen(navController: NavHostController, viewModel: MainViewModel) {

    val flag = remember { mutableStateOf(true) }
    val context = LocalContext.current
    //Переменная для кнопки обновления

    val user by viewModel.usercur

    LaunchedEffect(Unit) {
        viewModel.selectCurrentUserProfile()
    }

    if (user.isNotEmpty()) {
        val name = remember {
            mutableStateOf(user.last().name)
        }
        val second_name = remember {
            mutableStateOf(user.last().surname)
        }
        val patronymic = remember {
            mutableStateOf(user.last().patronymic)
        }
        val visit_count = remember {
            mutableStateOf(user.last().visitCount)
        }

        val isNameEnabled = remember { mutableStateOf(false) }
        val isLastNameEnabled = remember { mutableStateOf(false) }
        val isMiddleNameEnabled = remember { mutableStateOf(false) }
        //val isDateOfBirthEnabled = remember { mutableStateOf(false) }

        // Слой градиента 1
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF).copy(alpha = 0.8f),
                            Color(0xFF000000).copy(alpha = 0.9f)
                        ),
                        startY = 0.0f,
                        endY = 1500.0f
                    )
                )
        )
        // Слой градиента 2
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF99D77D).copy(alpha = 0.8f),
                            Color(0xFFFFFFFF).copy(alpha = 0.8f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    ),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.navigate("main_screen") }) {
                        Image(
                            painter = painterResource(id = R.drawable.backbutton),
                            contentDescription = "Back Button",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logomini),
                        contentDescription = "Center Logo",
                        modifier = Modifier.size(50.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Отображение полей профиля
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Фамилия
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .width(75.dp)
                                    .height(50.dp)
                                    .background(
                                        color = Color(0xFFD9D9D9),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = second_name.value, color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        color = Color(0xFF99D77D),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            isLastNameEnabled.value = !isLastNameEnabled.value
                                        },
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Last Name",
                                    tint = Color.White
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isLastNameEnabled.value,
                            Modifier.padding(8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedTextField(
                                    value = second_name.value,
                                    onValueChange = { second_name.value = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color(0xFFD9D9D9),
                                            RoundedCornerShape(10.dp)
                                        ),
                                    label = { Text("Введите фамилию", color = Color.Black) },
                                    textStyle = LocalTextStyle.current.copy(
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                )
                                Button(
                                    onClick = {
                                        viewModel.updateLastNameUserProfile(second_name.value)
                                        isLastNameEnabled.value =
                                            false // Скрываем поле после сохранения
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF99D77D
                                        )
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Сохранить", color = Color.White, fontSize = 16.sp)
                                }
                            }
                        }

                        // Имя
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .width(75.dp)
                                    .height(50.dp)
                                    .background(
                                        color = Color(0xFFD9D9D9),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = name.value, color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        color = Color(0xFF99D77D),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit First Name",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            isNameEnabled.value = !isNameEnabled.value
                                        }
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isNameEnabled.value,
                            Modifier.padding(8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedTextField(
                                    value = name.value,
                                    onValueChange = { name.value = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color(0xFFD9D9D9),
                                            RoundedCornerShape(10.dp)
                                        ),
                                    label = { Text("Введите имя", color = Color.Black) },
                                    textStyle = LocalTextStyle.current.copy(
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                )
                                Button(
                                    onClick = {
                                        viewModel.updateNameUserProfile(name.value)
                                        isNameEnabled.value =
                                            false // Скрываем поле после сохранения
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF99D77D
                                        )
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Сохранить", color = Color.White, fontSize = 16.sp)
                                }
                            }
                        }

                        // Отчество
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .width(75.dp)
                                    .height(50.dp)
                                    .background(
                                        color = Color(0xFFD9D9D9),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = patronymic.value ?: "", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        color = Color(0xFF99D77D),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            isMiddleNameEnabled.value =
                                                !isMiddleNameEnabled.value
                                        },
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Middle Name",
                                    tint = Color.White
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isMiddleNameEnabled.value,
                            Modifier.padding(8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedTextField(
                                    value = patronymic.value,
                                    onValueChange = { patronymic.value = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color(0xFFD9D9D9),
                                            RoundedCornerShape(10.dp)
                                        ),
                                    label = { Text("Введите отчество", color = Color.Black) },
                                    textStyle = LocalTextStyle.current.copy(
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                )
                                Button(
                                    onClick = {
                                        viewModel.updateMiddleNameUserProfile(patronymic.value)
                                        isMiddleNameEnabled.value =
                                            false // Скрываем поле после сохранения
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF99D77D
                                        )
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Сохранить", color = Color.White, fontSize = 16.sp)
                                }
                            }
                        }

                        // Количество посещений
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .width(75.dp)
                                    .height(50.dp)
                                    .background(
                                        color = Color(0xFFD9D9D9),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = visit_count.value.toString(), color = Color.Black)
                            }
                        }

                        //кнопка для перехода на страницу истории заказов
                        Button(
                            onClick = { navController.navigate("order_history_screen") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    0xFF99D77D
                                )
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Посмотреть историю заказов", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }

            // Navigation panel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
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
                    onClick = { Toast.makeText(context, "Добро пожаловать в профиль!", Toast.LENGTH_SHORT).show() },
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
    } else {
        CircularProgressIndicator()
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    UserProfileScreen(navController, MainViewModel())
}

