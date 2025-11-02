package com.example.proyectozonaslibros.ui.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
    onLoginExitoso: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    // valores actuales del formulario desde el ViewModel
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

        // Título
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

        // Campo Correo
        OutlinedTextField(
            value = correoActual,
            onValueChange = { loginViewModel.actualizarCorreo(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = claveActual,
            onValueChange = { loginViewModel.actualizarClave(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de error si la validación falla
        if (errorActual.isNotEmpty()) {
            Text(
                text = errorActual,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón Iniciar sesión
        Button(
            onClick = {
                val ok = loginViewModel.validarLogin()
                if (ok) {
                    onLoginExitoso(loginViewModel.loginData.value.correo)
                    // Aquí más adelante vamos a navegar al Home
                    // usando onLoginExitoso(correoActual)
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

        // Botón Registrarse
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
