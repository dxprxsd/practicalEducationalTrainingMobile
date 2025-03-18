package com.example.aphexbarbershop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aphexbarbershop.Domain.Constants
import com.example.aphexbarbershop.Models.Client
import com.example.aphexbarbershop.Models.Haircut
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
//import io.github.jan.supabase.gotrue.auth
//import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class MainViewModel : ViewModel()  {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    //uid текущего пользователя
    var currentUserUid = mutableStateOf<String?>(null)

    var profileCreated = mutableStateOf(false)

    private val _user = mutableStateOf(listOf<Client>())
    val user: State<List<Client>> = _user

    private val _rmhaircuts = mutableStateOf(listOf<Haircut>())
    val haircuts: State<List<Haircut>> = _rmhaircuts

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

    //Функция для регистрации в приложении
    fun onSignUpEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                // Регистрация пользователя
                val user = Constants.supabase.auth.signUpWith(Email) {
                    email = emailUser
                    password = passwordUser
                }

                // Сохранение ID текущего пользователя после успешной регистрации
                currentUserUid.value = Constants.supabase.auth.currentUserOrNull()?.id

                println("Current user uid: $currentUserUid")

                println("Registration Success")
                authResult.value = "Registration Success"
            } catch (e: Exception) {
                println("Registration Error")
                authResult.value = "Registration Error"
                println(e.message.toString())
            }
        }
    }


    fun fetchHaircuts() {
        // Запрос к базе данных (например, Supabase)
        viewModelScope.launch {
            try{
                val fetchedClothes = Constants.supabase.from("haircuts")
                    .select()
                    .decodeList<Haircut>()

                _rmhaircuts.value = fetchedClothes
                //filteredClothess() //применить фильтрацию
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки одежды: ${e.message}")
            }
        }
    }

    var visitc_ount = 1

    var statusAuto = 1

    //Функция для добавления данных о пользователе при регистрации
    fun addProfile(firstName: String, lastName: String, middleName: String, gender: Int, phonenumber: String) {
        viewModelScope.launch {
            try {
                Constants.supabase.from("client").insert(
                    Client(
                        uid = Constants.supabase.auth.currentUserOrNull()?.id!!,
                        name = firstName,
                        surname = lastName,
                        patronymic = middleName,
                        gender = gender,
                        phoneNumber = phonenumber,
                        visitCount = visitc_ount,
                        statusId = statusAuto
                    )
                )
                profileCreated.value = true
                Log.d("my_tag", "Profile successfully created")
            } catch (e: Exception) {
                Log.e("my_tag", "Error creating profile: ${e.message}")
                profileCreated.value = false
            }
        }
    }
}

