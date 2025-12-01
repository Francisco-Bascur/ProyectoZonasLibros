package com.example.proyectozonaslibros.viewmodel


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.proyectozonaslibros.storage.SessionManager

// Estado completo del formulario de registro.
// Contiene campos ingresados por el usuario, errores por campo,
// mensaje general y flag de éxito del registro.
data class RegisterFormState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmarClave: String = "",

    // Errores específicos para mostrar bajo cada TextField
    val nombreError: String? = null,
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

    // Estado observable por la UI
    var uiState by mutableStateOf(RegisterFormState())
        private set

    // --- Actualizadores de campo (se llaman desde la UI) ---
    fun onNombreChange(nuevo: String) {
        uiState = uiState.copy(
            nombre = nuevo,
            nombreError = null, // limpia el error cuando el usuario escribe
            mensajeGeneral = ""
        )
    }

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

    // -Validaciones individuales -
    private fun validarNombre(): String? {
        return when {
            uiState.nombre.isBlank() -> "El nombre es obligatorio"
            uiState.nombre.length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
    }
    // ------------------------------------------------------------
    // Validaciones internas (no se llaman desde la UI)
    // Devuelven null si el campo es válido o un mensaje de error si no lo es.
    private fun validarCorreo(): String? {
        val patternCorreo = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return when {
            uiState.correo.isBlank() -> "El correo es obligatorio"
            !patternCorreo.matches(uiState.correo) -> "Formato de correo no válido"
            else -> null
        }
    }

    private fun validarClave(): String? {
        return when {
            uiState.clave.isBlank() -> "La contraseña es obligatoria"
            uiState.clave.length < 6 -> "Debe tener mínimo 6 caracteres"
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

    // --- Acción principal: Registrar usuario ---
    // Realiza TODA la lógica del flujo de registro:
    // 1. Validar campos
    // 2. Actualizar errores
    // 3. Mostrar mensaje global si falla
    // 4. Guardar usuario en SessionManager
    // 5. Marcar registro como exitoso
    fun registrarUsuario() {
        // Ejecutar todas las validaciones individuales
        val nombreErr = validarNombre()
        val correoErr = validarCorreo()
        val claveErr = validarClave()
        val confirmarErr = validarConfirmarClave()

        //  Asignamos errores al estado para mostrarse en la UI
        uiState = uiState.copy(
            nombreError = nombreErr,
            correoError = correoErr,
            claveError = claveErr,
            confirmarClaveError = confirmarErr
        )

        // Si existe algún error, mostramos mensaje general y detenemos el proceso
        if (nombreErr != null || correoErr != null || claveErr != null || confirmarErr != null) {
            uiState = uiState.copy(
                mensajeGeneral = "Revisa los campos marcados en rojo",
                registroExitoso = false
            )
            return
        }

        //  Si todos está OK guardamos datos localmente persistencia local
        sessionManager.guardarUsuario(
            correo = uiState.correo,
            contrasena = uiState.clave
        )

        // registro exitoso  Alerta dialoj se mostrara en la pantalla
        uiState = uiState.copy(
            mensajeGeneral = "Cuenta creada con éxito ",
            registroExitoso = true
        )
    }

    // Para limpiar mensaje general cuando el usuario cierre el dialoj de éxito
    fun limpiarMensaje() {
        uiState = uiState.copy(mensajeGeneral = "")
    }
}
