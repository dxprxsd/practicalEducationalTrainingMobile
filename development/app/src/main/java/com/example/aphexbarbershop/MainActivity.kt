package com.example.aphexbarbershop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.Screens.LoadScreen
import com.example.aphexbarbershop.Screens.LoginScreen
import com.example.aphexbarbershop.Screens.MainScreen
import com.example.aphexbarbershop.Screens.OrderingOnHaircutScreen
import com.example.aphexbarbershop.Screens.RegistrationScreen
import com.example.aphexbarbershop.Screens.RegistrationScreenEmailPassword
import com.example.aphexbarbershop.Screens.UserProfileScreen
import com.example.aphexbarbershop.ui.theme.AphexBarbershopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AphexBarbershopTheme {
                val viewModel = MainViewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "load_screen") {

                    composable("load_screen") {
                        LoadScreen(navController)
                    }
                    composable("login_screen") {
                        LoginScreen(navController = navController, MainViewModel = viewModel)
                    }
                    composable("main_screen") {
                        MainScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("registration_screen") {
                        RegistrationScreenEmailPassword(navController = navController, MainViewModel = viewModel)
                    }
                    composable("registration_second_screen") {
                        RegistrationScreen(navController = navController, MainViewModel = viewModel)
                    }
                    composable("ordering_on_haircut_screen") {
                        OrderingOnHaircutScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("user_profile_screen") {
                        UserProfileScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
