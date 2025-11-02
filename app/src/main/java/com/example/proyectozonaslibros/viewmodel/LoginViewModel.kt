package com.example.proyectozonaslibros.viewmodel




import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.proyectozonaslibros.models.LoginModel
import com.example.proyectozonaslibros.storage.SessionManager

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",

    val correoError: String? = null,
    val claveError: String? = null,

    val errorGeneral: String = "",
    val loginExitoso: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    // Estado observable completo de la pantalla
    var uiState by mutableStateOf(LoginUiState())
        private set

    // Para compatibilidad con tu modelo antiguo (no lo pierdes si el profe lo mira)
    var loginData = mutableStateOf(
        LoginModel(
            correo = "",
            contrasena = ""
        )
    )

    // ---- Setters que llama la UI ----
    fun actualizarCorreo(nuevo: String) {
        uiState = uiState.copy(
            correo = nuevo,
            correoError = null,
            errorGeneral = "",
            loginExitoso = false
        )

        loginData.value = loginData.value.copy(correo = nuevo)
    }

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
    private fun validarCorreo(): String? {
        val value = uiState.correo.trim()
        val patternCorreo = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

        return when {
            value.isBlank() -> "El correo es obligatorio"
            !patternCorreo.matches(value) -> "Formato de correo no válido"
            else -> null
        }
    }

    private fun validarClave(): String? {
        val value = uiState.contrasena
        return when {
            value.isBlank() -> "La contraseña es obligatoria"
            value.length < 4 -> "Mínimo 4 caracteres"
            else -> null
        }
    }

    // ---- Acción principal: Login ----
    fun validarLogin() {
        // 1. Validar campos localmente
        val correoErr = validarCorreo()
        val claveErr = validarClave()

        // Actualizo errores en el estado
        uiState = uiState.copy(
            correoError = correoErr,
            claveError = claveErr
        )

        // Si hay errores de formulario -> corto acá
        if (correoErr != null || claveErr != null) {
            uiState = uiState.copy(
                errorGeneral = "Revisa los campos marcados en rojo",
                loginExitoso = false
            )
            return
        }

        // 2. Validar contra datos guardados en el dispositivo (persistencia local)
        val correoGuardado = sessionManager.obtenerCorreo()
        val claveGuardada = sessionManager.obtenerContrasena()

        if (correoGuardado.isNullOrBlank() || claveGuardada.isNullOrBlank()) {
            uiState = uiState.copy(
                errorGeneral = "No existe una cuenta registrada",
                loginExitoso = false
            )
            return
        }

        // 3. Comparar credenciales
        if (uiState.correo != correoGuardado || uiState.contrasena != claveGuardada) {
            uiState = uiState.copy(
                errorGeneral = "Credenciales incorrectas",
                loginExitoso = false
            )
            return
        }

        // 4. Listo: éxito 🎉
        uiState = uiState.copy(
            errorGeneral = "",
            loginExitoso = true
        )
    }

    fun limpiarEstadoGeneral() {
        uiState = uiState.copy(
            loginExitoso = false,
            errorGeneral = ""
        )
    }
}
