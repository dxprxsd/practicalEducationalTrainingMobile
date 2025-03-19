package com.example.aphexbarbershop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aphexbarbershop.Domain.Constants
import com.example.aphexbarbershop.Models.Appointment
import com.example.aphexbarbershop.Models.Client
import com.example.aphexbarbershop.Models.Employee
import com.example.aphexbarbershop.Models.Haircut
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
//import io.github.jan.supabase.gotrue.auth
//import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel : ViewModel() {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    //uid текущего пользователя
    var currentUserUid = mutableStateOf<String?>(null)

    var profileCreated = mutableStateOf(false)


    var dateOfBirth by mutableStateOf<String?>(null)
    var fname by mutableStateOf<String?>(null)
    var sname by mutableStateOf<String?>(null)
    var mname by mutableStateOf<String?>(null)



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

    private val _rmhaircuts = mutableStateOf(listOf<Haircut>())
    val haircuts: State<List<Haircut>> = _rmhaircuts


    var searchQuery = mutableStateOf("")

    private val _filteredHaircuts = mutableStateOf(listOf<Haircut>())

    val filteredHaircuts: State<List<Haircut>> = _filteredHaircuts

    //private val _rmclothes = mutableStateOf(listOf<Haircut>())
    //val clothes: State<List<Haircut>> = _rmclothes

    //для фильтрации по выбранным пунктам
    var typeOfReadyClothes by mutableStateOf<Int?>(null)



    // Метод для фильтрации по выбранным критериям
    fun filteredHaircutMethod() {
        _filteredHaircuts.value = _rmhaircuts.value
        //для фильтрации по выбранным пунктам
        if(typeOfReadyClothes != null){
            _filteredHaircuts.value = _filteredHaircuts.value.filter { Haircut -> Haircut.gender == typeOfReadyClothes }
        }
        // Фильтрация по поисковому запросу
        if(searchQuery.value.isNotEmpty()){
            _filteredHaircuts.value = _filteredHaircuts.value.filter { Haircut ->
                Haircut.name.lowercase(Locale.getDefault())
                    .contains(searchQuery.value.lowercase(Locale.getDefault()))
            }
        }
    }

    fun fetchHaircuts() {
        // Запрос к базе данных (например, Supabase)
        viewModelScope.launch {
            try {
                val fetchedClothes = Constants.supabase.from("haircuts")
                    .select()
                    .decodeList<Haircut>()

                _rmhaircuts.value = fetchedClothes
                filteredHaircutMethod() //применить фильтрацию
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки причесок: ${e.message}")
            }
        }
    }



    var visitc_ount = 1

    var statusAuto = 1

    //Функция для добавления данных о пользователе при регистрации
    fun addProfile(
        firstName: String,
        lastName: String,
        middleName: String,
        gender: Int,
        phonenumber: String
    ) {
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

    private val _hairtypes: MutableStateFlow<List<Haircut>> = MutableStateFlow(listOf())
    val haircutstypes: StateFlow<List<Haircut>> = _hairtypes.asStateFlow()

    private val _emploeetypes: MutableStateFlow<List<Employee>> = MutableStateFlow(listOf())
    val emploeesstypes: StateFlow<List<Employee>> = _emploeetypes.asStateFlow()

    fun getTypeHaircut() {
        // Запрос к базе данных (например, Supabase)
        viewModelScope.launch {
            try {
                val fetchedTypeHaircuts = Constants.supabase.from("haircuts")
                    .select()
                    .decodeList<Haircut>()

                _hairtypes.value = fetchedTypeHaircuts
                //filteredClothess() //применить фильтрацию
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки типов причесок: ${e.message}")
            }
        }
    }

    fun getTypeEmploee() {
        // Запрос к базе данных (например, Supabase)
        viewModelScope.launch {
            try {
                val fetchedTypeEmployee = Constants.supabase.from("employees")
                    .select() {
                        filter {
                            Employee::roleId eq 1
                            //or
                            eq("role_id", 1)
                        }
                    }

                    .decodeList<Employee>()

                _emploeetypes.value = fetchedTypeEmployee
                //filteredClothess() //применить фильтрацию
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки сотрудников: ${e.message}")
            }
        }
    }


    private val _user = mutableStateOf(listOf<Client>())
    val user: State<List<Client>> = _user
    // В ViewModel
    // Используем StateFlow вместо mutableStateOf
    //private val _user = MutableStateFlow<List<Client>>(emptyList())
    //val user: StateFlow<List<Client>> = _user

    // Function to fetch data
    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val currentUserId = Constants.supabase.auth.currentUserOrNull()?.id
                if (currentUserId != null) {
                    val clients = Constants.supabase.from("client").select {
                        filter {
                            Client::uid eq currentUserId
                        }
                    }.decodeList<Client>()
                    _user.value = clients
                    Log.d("my_tag", "User loaded: ${_user.value}")
                } else {
                    Log.e("my_tag", "Пользователь не авторизован")
                }
            } catch (e: Exception) {
                Log.e("my_tag", "Ошибка при загрузке пользователя: ${e.message}")
            }
        }
    }

    //Функция для получения данных о конкретном пользователе
//    fun selectProfile() {
//        viewModelScope.launch {
//            try {
//                _user.value = Constants.supabase.from("client").select {
//                    filter {
//                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
//                    }
//                }.decodeList<Client>()
//
//                Log.d("my_tag", "ДАННЫЕ ПОЛЬЗОВАТЕЛЯ: ${_user.value}")
//
//                Log.d("my_tag", "ДАННЫЕ ПОЛЬЗОВАТЕЛЯ ПОЛУЧЕНЫ")
//
//            } catch (e: Exception) {
//                Log.d("my_tag", e.message!!)
//            }
//        }
//    }

    private val _userr = mutableStateOf(listOf<Client>())
    val userr: State<List<Client>> = _userr


    //Функция для получения данных о конкретном пользователе
    fun selectProfileInfo() {
        viewModelScope.launch {
            try {
                _user.value = Constants.supabase.from("client").select {
                    filter {
                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                    }
                }.decodeList<Client>()

                Log.d("my_tag", "Success create profile")

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }

    private var _currentUserClientId: MutableLiveData<Int?> = MutableLiveData(null)
    val currentUserClientId: LiveData<Int?> = _currentUserClientId

    private val _userrc = mutableStateOf(listOf<Client>())
    val usercur: State<List<Client>> = _userrc
    
    //Функция для получения данных о конкретном пользователе
    fun selectCurrentUserProfile() {
        viewModelScope.launch {
            try {
                _userrc.value = Constants.supabase.from("client").select {
                    filter {
                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                    }
                }.decodeList<Client>()

                Log.d("my_tag", "Информация о пользователе найдена")

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }


    // Функция для получения данных пользователя и записи clientId
    fun selectProfile() {
        viewModelScope.launch {
            try {
                val currentUserId = Constants.supabase.auth.currentUserOrNull()?.id
                if (currentUserId != null) {
                    val clients = Constants.supabase.from("client").select {
                        filter {
                            Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                        }
                    }.decodeList<Client>()

                    // Проверяем, есть ли клиент и записываем его id
                    if (clients.isNotEmpty()) {
                        _currentUserClientId.value = clients.first().id
                        Log.d("my_tag", "ДАННЫЕ ПОЛЬЗОВАТЕЛЯ: ${clients.first()}")
                    } else {
                        Log.e("my_tag", "Клиент не найден в таблице clients")
                    }
                } else {
                    Log.e("my_tag", "Пользователь не авторизован")
                }
            } catch (e: Exception) {
                Log.e("my_tag", "Ошибка при загрузке данных пользователя: ${e.message}")
            }
        }
    }

    // Функция для добавления новой записи в appointments
    fun addNewAppointment(
        employeeId: Int,
        haircutId: Int,
        appointmentDate: String,
        finalPrice: Float
    ) {
        viewModelScope.launch {
            try {
                // Проверяем, что clientId загружен
                val clientId = currentUserClientId.value

                    Constants.supabase.from("appointments").insert(
                        Appointment(
                            clientId = clientId!!,
                            employeeId = employeeId,
                            haircutId = haircutId,
                            appointmentDate = appointmentDate,
                            finalPrice = finalPrice
                        )
                    )
                    Log.d("my_tag", "Запись успешно добавлена")
            } catch (e: Exception) {
                Log.e("my_tag", "Ошибка при добавлении записи: ${e.message}")
            }
        }
    }


    //Функция для обновления имени
    fun updateNameUserProfile(value: String) {
        viewModelScope.launch {
            try {

                Constants.supabase.from("client").update(
                    {
                        set("name_client", value)
                    }
                ) {
                    filter {
                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                    }
                }
                Log.d("my_tag", "Success update profile")

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }
    //Функция для обновления фамилии
    fun updateLastNameUserProfile(value: String) {
        viewModelScope.launch {
            try {
                Constants.supabase.from("client").update(
                    {
                        set("surname_client", value)
                    }
                ) {
                    filter {
                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                    }
                }
                Log.d("my_tag", "Success update profile")

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }
    //Функция для обновления отчества
    fun updateMiddleNameUserProfile(value: String) {
        viewModelScope.launch {
            try {

                Constants.supabase.from("client").update(
                    {
                        set("patronymic_client", value)
                    }
                ) {
                    filter {
                        Client::uid eq Constants.supabase.auth.currentUserOrNull()!!.id
                    }
                }
                Log.d("my_tag", "Success update profile")

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }

    //для выведения заказов кастома
    private val _customOrders = mutableStateOf(listOf<Appointment>())
    val customOrders: State<List<Appointment>> = _customOrders


    //Функция для получения данных заказах пользователя кастомных вещей
    fun selectInfoAboutUserAppointments(){
        viewModelScope.launch {
            try {
                // Получаем текущий UID пользователя
                val userUid = Constants.supabase.auth.currentUserOrNull()?.id

                if(userUid != null) {

                    // Получаем пользователя по UID из таблицы "users"
                    val userResponse = Constants.supabase.from("client").select {
                        filter {
                            Client::uid eq userUid // Сравниваем с userUid
                        }
                    }.decodeList<Client>()

                    val userIdFromDb = userResponse.lastOrNull()?.id // Получаем ID пользователя из ответа базы данных

                    _customOrders.value = Constants.supabase.from("appointments").select {
                        filter {
                            Appointment::clientId eq userIdFromDb
                        }
                    }.decodeList<Appointment>()

                    Log.d("my_tag", "Success select info about custom orders")
                } else {
                    Log.d("my_tag", "Ошибка: currentUserUid не задан.")
                }

            } catch (e: Exception) {
                Log.d("my_tag", e.message!!)
            }
        }
    }

}



