package com.example.proyectozonaslibros.ui.login

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService



import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    onLoginExitoso: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = loginViewModel.uiState
    val correoActual = state.correo
    val claveActual = state.contrasena
    // recurso nativo vibracion error
    @Suppress("MissingPermission")
    fun vibrarError() {
        val vibrator = context.getSystemService<Vibrator>() ?: return

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {

            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }
    }



    // 🔔 Si login fue exitoso mostramos Dialog y navegamos cuando acepte
    if (state.loginExitoso) {
        AlertDialog(
            onDismissRequest = {
                loginViewModel.limpiarEstadoGeneral()
                onLoginExitoso(correoActual)
            },
            title = {
                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Inicio de sesión exitoso ",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        loginViewModel.limpiarEstadoGeneral()
                        onLoginExitoso(correoActual)
                        Toast.makeText(context, "Sesión iniciada", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Continuar")
                }
            }
        )
    }

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- Título ---
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

        // --- Campo Correo ---
        OutlinedTextField(
            value = correoActual,
            onValueChange = { loginViewModel.actualizarCorreo(it) },
            label = { Text("Correo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "icono correo"
                )
            },
            isError = state.correoError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.correoError != null) {
            Text(
                text = state.correoError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo Contraseña ---
        OutlinedTextField(
            value = claveActual,
            onValueChange = { loginViewModel.actualizarClave(it) },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "icono contraseña"
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.claveError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.claveError != null) {
            Text(
                text = state.claveError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Mensaje general de error ---
        if (state.errorGeneral.isNotEmpty() && !state.loginExitoso) {
            Text(
                text = state.errorGeneral,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- Botón Iniciar sesión ---
        Button(
            onClick = {
                loginViewModel.validarLogin()
                if (!state.loginExitoso && state.errorGeneral.isNotEmpty()) {

                    vibrarError()// vibra cuando hay error
                    Toast.makeText(context, state.errorGeneral, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3949AB)
            ),
            enabled = !state.loginExitoso // evita spam después del éxito
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
