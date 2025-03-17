package com.example.aphexbarbershop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aphexbarbershop.Domain.Constants
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
//import io.github.jan.supabase.gotrue.auth
//import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class MainViewModel : ViewModel()  {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    //uid текущего пользователя
    var currentUserUid = mutableStateOf<String?>(null)

    //private val _user = mutableStateOf(listOf<users>())
    //val user: State<List<users>> = _user

    //Функция для входа в приложении
    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                // Вход пользователя
                val user = Constants.supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }

                // Сохранение ID текущего пользователя
                currentUserUid.value = Constants.supabase.auth.currentUserOrNull()?.id

                println("Current user uid: $currentUserUid")

                println("Success")
                authResult.value = "Success" // Устанавливаем успешный результат
            } catch (e: Exception) {
                println("Error")
                authResult.value = "Error" // Устанавливаем ошибочный результат
                println(e.message.toString())
            }
        }
    }
}