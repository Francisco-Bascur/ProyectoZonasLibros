package com.example.proyectozonaslibros.ui.login



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val state = registerViewModel.uiState

    // Si el registro fue exitoso, mostramos un AlertDialog "bonito"
    if (state.registroExitoso) {
        AlertDialog(
            onDismissRequest = {
                registerViewModel.limpiarMensaje()
                onNavigateToLogin()
            },
            title = {
                Text(
                    text = "Cuenta creada",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Tu cuenta se creó correctamente.\nAhora puedes iniciar sesión.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        registerViewModel.limpiarMensaje()
                        onNavigateToLogin()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Crear cuenta",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // NOMBRE
        OutlinedTextField(
            value = state.nombre,
            onValueChange = { registerViewModel.onNombreChange(it) },
            label = { Text("Nombre") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "icono nombre"
                )
            },
            isError = state.nombreError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.nombreError != null) {
            Text(
                text = state.nombreError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CORREO
        OutlinedTextField(
            value = state.correo,
            onValueChange = { registerViewModel.onCorreoChange(it) },
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

        // CLAVE
        OutlinedTextField(
            value = state.clave,
            onValueChange = { registerViewModel.onClaveChange(it) },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "icono clave"
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

        // CONFIRMAR CLAVE
        OutlinedTextField(
            value = state.confirmarClave,
            onValueChange = { registerViewModel.onConfirmarClaveChange(it) },
            label = { Text("Confirmar contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "icono confirmar clave"
                )
            },
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

        // BOTÓN CREAR CUENTA
        Button(
            onClick = { registerViewModel.registrarUsuario() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.registroExitoso // para no spamear después del éxito
        ) {
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mensaje general (error global del formulario)
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

        // Botón volver a Login
        TextButton(onClick = { onNavigateToLogin() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

