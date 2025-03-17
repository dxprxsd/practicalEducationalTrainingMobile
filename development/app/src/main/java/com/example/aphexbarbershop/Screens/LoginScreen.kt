package com.example.aphexbarbershop.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.R

@Composable
fun LoginScreen(navController: NavHostController, MainViewModel: MainViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authResult by MainViewModel.authResult

    LaunchedEffect(authResult) {
        if (authResult == "Success") {
            navController.navigate("main_screen") { //main_screen
                popUpTo("auth_screen") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF565656)), // Темный фон
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Верхний блок с логотипом и заголовком
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.aphexlogobarber),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp)
            )
            Text(
                text = "Авторизация",
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Поле ID
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText -> email = newText },
                    label = { Text("Логин") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Поле Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText -> password = newText },
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                // Текст-кнопка "Забыли пароль?"
                Text(
                    text = "Зарегистрироваться в системе",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        // TODO: Добавить логику перехода на страницу регистрации
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Кнопка "Продолжить"
                Button(
                    onClick = {
                        MainViewModel.onSignInEmailPassword(email, password)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98E58C)), // Зеленый цвет кнопки
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Продолжить",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController, MainViewModel())
}
