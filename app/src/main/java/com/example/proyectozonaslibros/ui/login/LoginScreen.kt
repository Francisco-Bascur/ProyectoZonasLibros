package com.example.proyectozonaslibros.ui.login



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    // sacamos los valores actuales para usarlos más fácil
    val correoActual = loginViewModel.loginData.value.correo
    val claveActual = loginViewModel.loginData.value.contrasena
    val errorActual = loginViewModel.errorMensaje.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3949AB),
                        Color(0xFF5C6BC0),
                        Color(0xFFE8EAF6)
                    )
                )
            )
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // --- Título principal ---
        Text(
            text = "Iniciar sesión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        // --- Campo CORREO ---
        OutlinedTextField(
            value = correoActual,
            onValueChange = { loginViewModel.actualizarCorreo(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo CONTRASEÑA ---
        OutlinedTextField(
            value = claveActual,
            onValueChange = { loginViewModel.actualizarClave(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Mensaje de error (si hay) ---
        if (errorActual.isNotEmpty()) {
            Text(
                text = errorActual,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- Botón de INICIO DE SESIÓN ---
        Button(
            onClick = {
                val ok = loginViewModel.validarLogin()
                if (ok) {
                    // Aquí va la acción cuando el login es válido.
                    // En el siguiente avance: navegar a HomeScreen.
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3949AB)
            )
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Botón de REGISTRO ---
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

