package com.example.aphexbarbershop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aphexbarbershop.Screens.LoadScreen
import com.example.aphexbarbershop.Screens.LoginScreen
import com.example.aphexbarbershop.Screens.LoginScreenPreview
import com.example.aphexbarbershop.Screens.MainScreen
import com.example.aphexbarbershop.Screens.RegistrationScreen
import com.example.aphexbarbershop.Screens.RegistrationScreenEmailPassword
import com.example.aphexbarbershop.Screens.RegistrationScreenEmailPasswordScreenPreview
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
                        LoginScreen(navController = navController, MainViewModel = MainViewModel())
                    }
                    composable("main_screen") {
                        MainScreen(navController = navController, MainViewModel = MainViewModel())
                    }
                    composable("registration_screen") {
                        RegistrationScreenEmailPassword(navController = navController, MainViewModel = MainViewModel())
                    }
                    composable("registration_second_screen") {
                        RegistrationScreen(navController = navController, MainViewModel = MainViewModel())
                    }
                }
            }
        }
    }
}
