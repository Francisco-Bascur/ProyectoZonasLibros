package com.example.proyectozonaslibros.ui.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onVolverLogin: () -> Unit,
    onRegistroExitoso: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
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
        Text(
            text = "Crear cuenta",
            fontSize = 28.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campos de texto
        OutlinedTextField(
            value = registerViewModel.nombre.value,
            onValueChange = { registerViewModel.nombre.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = registerViewModel.correo.value,
            onValueChange = { registerViewModel.correo.value = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = registerViewModel.clave.value,
            onValueChange = { registerViewModel.clave.value = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = registerViewModel.confirmarClave.value,
            onValueChange = { registerViewModel.confirmarClave.value = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje de error (si hay)
        if (registerViewModel.errorMensaje.value.isNotEmpty()) {
            Text(
                text = registerViewModel.errorMensaje.value,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Crear cuenta
        Button(
            onClick = {
                val ok = registerViewModel.validarRegistro()
                if (ok) {
                    onRegistroExitoso() // vuelve automáticamente al login
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
        ) {
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { onVolverLogin() },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF3949AB)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al login")
        }
    }
}
