package com.example.proyectozonaslibros.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.proyectozonaslibros.models.LoginModel
import com.example.proyectozonaslibros.storage.SessionManager

// Modelo de estado que representa todos los valores de la pantalla de Login.
// Incluye los campos del formulario, errores y si el login fue exitoso.
data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",

    //  Errores específicos por campo
    val correoError: String? = null,
    val claveError: String? = null,

//  Error general credenciales incorrectas
    val errorGeneral: String = "",
    val loginExitoso: Boolean = false
)


//  ViewModel que maneja el estado del Login.
// Hereda de AndroidViewModel para poder usar el contexto (SessionManager).
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Clase encargada de leer/guardar datos en SharedPreferences.
    private val sessionManager = SessionManager(application)

    // Estado observable para Compose
    // Cualquier cambio aquí actualiza automáticamente la UI.
    var uiState by mutableStateOf(LoginUiState())
        private set

    // se mantiene compatibilidad
    var loginData = mutableStateOf(
        LoginModel(
            correo = "",
            contrasena = ""
        )
    )

    // ---- Setters que llama la UI ----
    //  Actualiza el correo cuando el usuario escribe en el TextField.
    // Limpia errores para que desaparezcan al corregir.
    // También resetea el estado de login exitoso.
    fun actualizarCorreo(nuevo: String) {
        uiState = uiState.copy(
            correo = nuevo,
            correoError = null,
            errorGeneral = "",
            loginExitoso = false
        )
         // Compatibilidad con el modelo antiguo
        loginData.value = loginData.value.copy(correo = nuevo)
    }
    // funcion que Actualiza la contraseña en el estado.
    // Limpia errores  para mejorar la UX.
    fun actualizarClave(nueva: String) {
        uiState = uiState.copy(
            contrasena = nueva,
            claveError = null,
            errorGeneral = "",
            loginExitoso = false
        )

        loginData.value = loginData.value.copy(contrasena = nueva)
    }

    // ---- Validaciones internas ----
    //  Validación interna del correo.
    // Retorna null si el correo es válido.
    // Retorna string si existe un error , !este mensaje se muestra  en pantalla
    private fun validarCorreo(): String? {
        val value = uiState.correo.trim()
        val patternCorreo = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

        return when {
            value.isBlank() -> "El correo es obligatorio"
            !patternCorreo.matches(value) -> "Formato de correo no válido"
            else -> null
        }
    }

    //     Validación de la contraseña.
    //    Se revisa que no esté vacía y que cumpla con longitud mínima 4 caract.
    private fun validarClave(): String? {
        val value = uiState.contrasena
        return when {
            value.isBlank() -> "La contraseña es obligatoria"
            value.length < 4 -> "Mínimo 4 caracteres"
            else -> null
        }
    }

    // ---- Acción principal: validar Login ----
    //  Función principal de login.
    // Paso 1 Validar correo y contraseña localmente.
    // Paso 2 Revisar que existan credenciales guardadas.
    // Paso 3 Comparar credenciales ingresadas con las almacenadas.
    // Paso 4 sitodo está bien, marcar login como exitoso.
    fun validarLogin() {
        // 1. Validar campos localmente
        val correoErr = validarCorreo()
        val claveErr = validarClave()

        // Actualizo errores en el estado , Si hay errores, se reflejan en pantalla
        uiState = uiState.copy(
            correoError = correoErr,
            claveError = claveErr
        )

        // Si hay errores de formulario, se deteiene el proceso
        if (correoErr != null || claveErr != null) {
            uiState = uiState.copy(
                errorGeneral = "credenciales incorrectas",
                loginExitoso = false
            )
            return
        }

        //  Validar que haya una cuenta registrada en el dispositivo (persistencia local)
        val correoGuardado = sessionManager.obtenerCorreo()
        val claveGuardada = sessionManager.obtenerContrasena()

        if (correoGuardado.isNullOrBlank() || claveGuardada.isNullOrBlank()) {
            uiState = uiState.copy(
                errorGeneral = "No existe una cuenta registrada",
                loginExitoso = false
            )
            return
        }

        // 3. Comparar credenciales ingresadas vs guardadas
        if (uiState.correo != correoGuardado || uiState.contrasena != claveGuardada) {
            uiState = uiState.copy(
                errorGeneral = "Credenciales incorrectas",
                loginExitoso = false
            )
            return
        }

        // 4. si es exitoso, se  muestra el AlertDialog y navega al Home.
        uiState = uiState.copy(
            errorGeneral = "",
            loginExitoso = true
        )
    }
// limpiar el estado después del dialog.
// funcion Es llamado cuando el usuario presiona continuar .
    fun limpiarEstadoGeneral() {
        uiState = uiState.copy(
            loginExitoso = false,
            errorGeneral = ""
        )
    }
}
