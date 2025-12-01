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
    val correo = loginViewModel.loginModel.correo
    val contrasena = loginViewModel.loginModel.contrasena

    // 🔹 ALERTA (errores o éxito)
    if (loginViewModel.mostrarAlerta) {
        ShowAlert(
            titulo = loginViewModel.tituloAlerta,
            mensaje = loginViewModel.mensajeAlerta,
            textoBtnConfirmar = loginViewModel.textoBotonAlerta,
            onConfirm = {
                loginViewModel.descartarAlerta()

                // Si las credenciales eran correctas → navegar al Home
                if (loginViewModel.deberiamosNavegar) {
                    onLoginExitoso()
                    loginViewModel.cambiarEstadoNavegacion()
                }
            }
        )
    }

    // 🔹 UI LOGIN
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        OutlinedTextField(
            value = correo,
            onValueChange = { loginViewModel.cambioCorreo(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { loginViewModel.cambioContrasena(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { loginViewModel.auth() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3949AB)
            ),
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(24.dp))

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





