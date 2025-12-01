package com.example.proyectozonaslibros.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.proyectozonaslibros.ui.home.HomeScreen
import com.example.proyectozonaslibros.ui.login.LoginScreen
import com.example.proyectozonaslibros.ui.login.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"

    )

    {
        // Pantalla Login
        composable(route = "login") {
            LoginScreen(
                // Navega a pantalla de registro
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginExitoso = {
                    navController.navigate("home"){
                        popUpTo("login"){inclusive = true}
                    }
                }
            )
        }
        //  Pantalla Registro
        composable(route = "register") {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        //  Pantalla Home con botón de Cerrar Sesión
        composable(route = "home"){
        HomeScreen(
                onLogout = {

                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
