package com.example.proyectozonaslibros.ui.login

import com.example.proyectozonaslibros.helper.ShowAlert
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.viewmodel.RegisterViewModel

@Composable

// funcion de navegacion register
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val state = registerViewModel.uiState

    // Si el registro fue exitoso, mostramos un AlertDialog reutilizable
    if (state.registroExitoso) {
        ShowAlert(
            titulo = "Cuenta creada",
            mensaje = "Tu cuenta se creó correctamente.",
            textoBtnConfirmar = "Aceptar",
            onConfirm = {
                registerViewModel.limpiarMensaje()
                onNavigateToLogin()
            },

        )
    }

    // Columna principal centrada con padding general
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //  Título principal de la pantalla Crear Cuenta
        Text(
            text = "Registro Usuario",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))



        // CORREO electronico del usuario
        OutlinedTextField(
            value = state.correo,
            onValueChange = { registerViewModel.onCorreoChange(it) },
            label = { Text("Correo") },
            isError = state.correoError != null,
            modifier = Modifier.fillMaxWidth()
        )

        // Muestra el mensaje de error del correo SOLO cuando existe un error
        if (state.correoError != null) {
            // Error contextual del correo
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

        // CAMPO contraseña del usuario
        OutlinedTextField(
            value = state.clave,
            onValueChange = { registerViewModel.onClaveChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.claveError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.claveError != null) {
            //  Error  de contraseña
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

        // CAMPO CONFIRMAR contraseña
        OutlinedTextField(
            value = state.confirmarClave,
            onValueChange = { registerViewModel.onConfirmarClaveChange(it) },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.confirmarClaveError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.confirmarClaveError != null) {
            Text(
                text = state.confirmarClaveError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BOTÓN CREAR CUENTA , el ViewModel devuelve true si el registro es válido.
        Button(
            onClick = { registerViewModel.registrarUsuario() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.registroExitoso // evita clics después del éxito
        ) {
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //  Mensaje de error general del formulario (si existe)
        if (state.mensajeGeneral.isNotEmpty() && !state.registroExitoso) {
            Text(
                text = state.mensajeGeneral,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de accion  volver a Login
        TextButton(onClick = { onNavigateToLogin() }) {
            Text("volver")
        }
    }
}

