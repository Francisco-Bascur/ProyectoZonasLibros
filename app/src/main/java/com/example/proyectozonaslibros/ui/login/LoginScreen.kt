package com.example.proyectozonaslibros.ui.login
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.proyectozonaslibros.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    // Diseño de fondo con degradado
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
                .padding(24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // --- Campo CORREO ---
        OutlinedTextField(
            value = loginViewModel.correo,
            onValueChange = { loginViewModel.cambioCorreo(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            isError = loginViewModel.correo.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo CONTRASEÑA ---
        OutlinedTextField(
            value = loginViewModel.clave,
            onValueChange = { loginViewModel.cambioClave(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = loginViewModel.clave.isBlank()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- Botón de INICIO DE SESIÓN ---
        Button(
            onClick = { loginViewModel.validarLogin() },
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

    // --- ALERTA (POPUP) ---
    if (loginViewModel.verAlerta) {
        AlertDialog(
            onDismissRequest = { loginViewModel.cerrarAlerta() },
            confirmButton = {
                TextButton(onClick = { loginViewModel.cerrarAlerta() }) {
                    Text(loginViewModel.textoBtnAlerta)
                }
            },
            title = { Text(loginViewModel.tituloAlerta) },
            text = { Text(loginViewModel.mensajeAlerta) }
        )
    }
}
