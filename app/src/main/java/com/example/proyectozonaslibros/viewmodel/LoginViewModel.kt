package com.example.proyectozonaslibros.viewmodel




import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyectozonaslibros.models.LoginModel

class LoginViewModel : ViewModel() {

    // Estado del formulario de login usando tu modelo
    var loginData = mutableStateOf(
        LoginModel(
            correo = "",
            contrasena = ""
        )
    )

    // Mensaje de error para mostrar en pantalla si algo falla
    var errorMensaje = mutableStateOf("")

    // Valida las credenciales ingresadas
    fun validarLogin(): Boolean {
        val correoActual = loginData.value.correo
        val claveActual = loginData.value.contrasena

        // 1. Campos vacíos
        if (correoActual.isBlank() || claveActual.isBlank()) {
            errorMensaje.value = "Correo y clave no pueden estar vacíos"
            return false
        }

        // 2. Formato básico de correo
        if (!correoActual.contains("@")) {
            errorMensaje.value = "El correo no tiene un formato válido"
            return false
        }

        // 3. Largo mínimo de la clave
        if (claveActual.length < 4) {
            errorMensaje.value = "La contraseña debe tener al menos 4 caracteres"
            return false
        }

        // 4. Si todo está bien
        errorMensaje.value = ""
        return true
    }

    // Actualiza el correo dentro del modelo
    fun actualizarCorreo(nuevoCorreo: String) {
        loginData.value = loginData.value.copy(
            correo = nuevoCorreo
        )
    }

    // Actualiza la clave dentro del modelo
    fun actualizarClave(nuevaClave: String) {
        loginData.value = loginData.value.copy(
            contrasena = nuevaClave
        )
    }
}
