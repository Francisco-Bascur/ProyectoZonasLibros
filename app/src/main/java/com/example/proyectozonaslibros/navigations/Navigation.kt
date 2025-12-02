package com.example.proyectozonaslibros.navigations


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectozonaslibros.ui.home.AgregarLibroScreen
import com.example.proyectozonaslibros.ui.home.EditarLibroScreen
import com.example.proyectozonaslibros.ui.home.HomeScreen


import com.example.proyectozonaslibros.ui.login.LoginScreen
import com.example.proyectozonaslibros.ui.login.RegisterScreen
import com.example.proyectozonaslibros.viewmodel.LibroViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val libroViewModel: LibroViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"

    ){

        // Pantalla Login
        composable(route = "login") {
            LoginScreen(
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
        composable(route = "home") {
            HomeScreen(
                navController = navController,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                libroViewModel = libroViewModel
            )
        } // agregar libro usa viewModel
        composable("agregarLibro") {
            AgregarLibroScreen(
                navController = navController,
                libroViewModel = libroViewModel
            )

        }

          // EDITAR LIBRO
        composable("editarLibro") {
            EditarLibroScreen(
                navController = navController,
                libroViewModel = libroViewModel
            )
        }


    }
}
