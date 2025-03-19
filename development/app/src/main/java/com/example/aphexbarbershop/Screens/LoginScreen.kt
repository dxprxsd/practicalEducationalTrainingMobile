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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.style.TextAlign
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start // Align to the left
                ) {
                    Text(
                        text = "Логин", // The label for the email field
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Left
                    )
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, Color(0xFF99D77D)), RoundedCornerShape(10.dp))
                        .padding(1.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp)) // Spacing between the fields


                // Поле Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start // Align to the left
                ) {
                    Text(
                        text = "Пароль", // The label for the password field
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Left
                    )
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, Color(0xFF99D77D)), RoundedCornerShape(10.dp))
                        .padding(1.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Текст-кнопка "Забыли пароль?"
                Text(
                    text = "Зарегистрироваться в системе",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.navigate("registration_screen")
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
