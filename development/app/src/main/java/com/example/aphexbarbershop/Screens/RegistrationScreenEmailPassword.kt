package com.example.aphexbarbershop.Screens

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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.R

@Composable
fun RegistrationScreenEmailPassword(navController: NavHostController, MainViewModel: MainViewModel) {
    val context = LocalContext.current
    var email = remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //var confirmPassword by remember { mutableStateOf("") }

    val authResult by MainViewModel.authResult

    LaunchedEffect(authResult) {
        // Следим за изменением значения authResult
        if (authResult == "Registration Success") {
            // Если результат регистрации успешен, выполняем навигацию
            navController.navigate("registration_second_screen") {
                // Очищаем стек навигации, чтобы не было возврата на экран авторизации
                popUpTo("login_screen") { inclusive = true }
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
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Распределяем пространство между элементами
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Логотип (левый)
            IconButton(onClick = { navController.navigate("auth_screen") }) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = "Left Logo",
                    modifier = Modifier.size(40.dp), // Устанавливаем размер изображения
                )
            }
            Text(
                text = "Регистрация",
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start // Выровнять по левому краю
                ){
                    Text(
                        text = "Почта",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Left
                    )
                }
                OutlinedTextField(value = email.value, onValueChange = { email.value = it }, modifier = Modifier
                    .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFF99D77D)), RoundedCornerShape(10.dp))
                    .padding(1.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black))
                Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстовыми полями

                // Регистрация пароль
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start // Выровнять по левому краю
                ){
                    Text(
                        text = "Пароль",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Left
                    )
                }
                OutlinedTextField(value = password, onValueChange = { password = it }, modifier = Modifier
                    .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFF99D77D)), RoundedCornerShape(10.dp))
                    .padding(1.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black))
                Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстовыми полями


                Spacer(modifier = Modifier.weight(1f))

                // Кнопка "Продолжить"
                Button(
                    onClick = {
                        MainViewModel.onSignUpEmailPassword(email.value, password)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98E58C)), // Зеленый цвет кнопки
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Далее",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }// Отображение сообщения об ошибке при неудачной регистрации
                if (MainViewModel.authResult.value == "Registration Error") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Ошибка", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

//Функция для проверки шаблона почты
private fun String.isEmailValid(): Boolean {
    return Regex("^(.+)@(.+)\\.(.+)$").matches(this)
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenEmailPasswordScreenPreview() {
    // Создаем NavHostController для превью
    val navController = rememberNavController()
    RegistrationScreenEmailPassword(navController, MainViewModel()) // Передаем контроллер в LoadScreen
}