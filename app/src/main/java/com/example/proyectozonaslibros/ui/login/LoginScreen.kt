package com.example.proyectozonaslibros.ui.login
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.helper.ShowAlert
import com.example.proyectozonaslibros.viewmodel.LoginViewModel

@Composable

fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginExitoso: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    //  se Accede al estado actual del ViewModel
    val state = loginViewModel.uiState

    // 🔹 Mostrar alerta cuando el login es exitoso
    if (state.loginExitoso) {
        ShowAlert(
            titulo = "Inicio de sesion exitoso!",
            mensaje = "Bienvenido....",
            textoBtnConfirmar = "Continuar",
            onConfirm = {
                loginViewModel.limpiarEstadoGeneral()
                onLoginExitoso()   // NAVEGAR AL HOME
            }
        )
    }

//  Mostrar alerta cuando haya un error general
    if (state.errorGeneral.isNotEmpty()) {
        ShowAlert(
            titulo = "Error de inicio de sesión",
            mensaje = state.errorGeneral,
            textoBtnConfirmar = "Aceptar",
            onConfirm = {
                loginViewModel.limpiarEstadoGeneral()
            },

        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text iniciar session
        Text(
            text = "Inicio sesión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        // Campo Correo para ingresar correo
        OutlinedTextField(
            value = state.correo,
            onValueChange = { loginViewModel.actualizarCorreo(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña para ingresar la contraseña
        OutlinedTextField(
            value = state.contrasena,
            onValueChange = { loginViewModel.actualizarClave(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Botón principal iniciar session
        Button(
            onClick = {
                loginViewModel.validarLogin()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3949AB)
            ),
        ) {
            Text("Iniciar sesión")
        }


        Spacer(modifier = Modifier.height(24.dp))

        // --- Botón Registrarse ---
        OutlinedButton(
            onClick = { onNavigateToRegister() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF3949AB)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}
// correcion de errores