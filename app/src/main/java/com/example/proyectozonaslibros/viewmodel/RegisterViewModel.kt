package com.example.proyectozonaslibros.viewmodel
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.proyectozonaslibros.storage.SessionManager

// Estado completo del formulario de registro.
data class RegisterFormState(
    val correo: String = "",
    val clave: String = "",
    val confirmarClave: String = "",

    // Errores específicos para mostrar bajo cada TextField
    val correoError: String? = null,
    val claveError: String? = null,
    val confirmarClaveError: String? = null,

//  Mensaje general  - Revisa los campos o Cuenta creada
    val mensajeGeneral: String = "",
    val registroExitoso: Boolean = false
)
class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // SessionManager para guardar datos localmente (persistencia local)
    private val sessionManager = SessionManager(application)

    var uiState by mutableStateOf(RegisterFormState())
        private set

    fun onCorreoChange(nuevo: String) {
        uiState = uiState.copy(
            correo = nuevo,
            correoError = null,
            mensajeGeneral = ""
        )
    }

    fun onClaveChange(nuevo: String) {
        uiState = uiState.copy(
            clave = nuevo,
            claveError = null,
            mensajeGeneral = ""
        )
    }

    fun onConfirmarClaveChange(nuevo: String) {
        uiState = uiState.copy(
            confirmarClave = nuevo,
            confirmarClaveError = null,
            mensajeGeneral = ""
        )
    }

    private fun validarCorreo(): String? {
        val correo = uiState.correo.trim()

        return when {
            correo.isBlank() -> "El correo es obligatorio"
            !correo.contains("@") -> "Debe incluir @"
            else -> null
        }
    }
    private fun validarClave(): String? {
        return when {
            uiState.clave.isBlank() -> "La contraseña es obligatoria"
            uiState.clave.length < 4 -> "Debe tener mínimo 4 caracteres"
            else -> null
        }
    }
    private fun validarConfirmarClave(): String? {
        return when {
            uiState.confirmarClave.isBlank() -> "Debes repetir la contraseña"
            uiState.confirmarClave != uiState.clave -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    fun registrarUsuario() {
        // Ejecutar todas las validaciones individuales
        val correoErr = validarCorreo()
        val claveErr = validarClave()
        val confirmarErr = validarConfirmarClave()
        //  Asignamos errores al estado para mostrarse en la UI
        uiState = uiState.copy(
            correoError = correoErr,
            claveError = claveErr,
            confirmarClaveError = confirmarErr
        )

        // Si existe algún error, mostramos mensaje general y detenemos el proceso
        if (correoErr != null || claveErr != null || confirmarErr != null) {
            uiState = uiState.copy(
                mensajeGeneral = "los Campos Son Obligatorios",
                registroExitoso = false
            )
            return
        }
        //  Si todos está OK guardamos datos localmente persistencia local
        sessionManager.guardarUsuario(
            correo = uiState.correo,
            contrasena = uiState.clave
        )
        uiState = uiState.copy(
            mensajeGeneral = "Cuenta creada ! ",
            registroExitoso = true
        )
    }
    fun limpiarMensaje() {
        uiState = uiState.copy(
            correo = "",
            clave = "",
            confirmarClave = "",
            correoError = null,
            claveError = null,
            confirmarClaveError = null,
            mensajeGeneral = "",
            registroExitoso = false
        )
    }

}