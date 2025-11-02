package com.example.proyectozonaslibros.navigations


import com.example.proyectozonaslibros.ui.login.home.HomeScreen


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectozonaslibros.ui.login.LoginScreen
import com.example.proyectozonaslibros.ui.login.RegisterScreen

@Composable

fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Pantalla Login
        composable(route = "login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginExitoso = { correoUsuario ->
                    navController.navigate("home/$correoUsuario")
                }
            )
        }

        // Pantalla Registro
        composable(route = "register") {
            RegisterScreen(
                onVolverLogin = {
                    navController.popBackStack()
                },
                onRegistroExitoso = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla Home
        composable(route = "home/{userEmail}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("userEmail") ?: ""
            HomeScreen(userEmail = email)
        }
    }
}
