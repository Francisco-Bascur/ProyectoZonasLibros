package com.example.proyectozonaslibros.navigations



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

        // Pantalla de inicio de sesión
        composable(route = "login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // Pantalla de registro
        composable(route = "register") {
            RegisterScreen(
                onVolverLogin = {
                    // volver manualmente al login (botón "Volver al login")
                    navController.popBackStack()
                },
                onRegistroExitoso = {
                    // volver automáticamente al login después de registro válido
                    navController.popBackStack()
                }
            )
        }
    }
}
