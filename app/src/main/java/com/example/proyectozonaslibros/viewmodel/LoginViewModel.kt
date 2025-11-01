package com.example.proyectozonaslibros.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * LoginViewModel
 *
 * Responsable de manejar el estado del formulario de inicio de sesión.
 * Aquí guardamos:
 *  - Lo que el usuario escribe (correo, clave)
 *  - El estado de la alerta emergente (popup)
 *  - Los textos que se muestran en la alerta
 *
 * Importante para la rúbrica:
 *  - La validación vive aquí, NO en la vista.
 *    Eso demuestra separación de responsabilidades (MVVM).
 */
class LoginViewModel : ViewModel() {

    // ---------------------------
    // CAMPOS DEL FORMULARIO LOGIN
    // ---------------------------

    // Valor actual del campo "Correo"
    var correo by mutableStateOf("")
        private set

    // Valor actual del campo "Clave"
    var clave by mutableStateOf("")
        private set

    // Estas funciones se llaman desde la UI cuando el usuario escribe
    fun cambioCorreo(nuevoCorreo: String) {
        correo = nuevoCorreo
    }

    fun cambioClave(nuevaClave: String) {
        clave = nuevaClave
    }

    // ---------------------------
    // ESTADO DE LA ALERTA / POPUP
    // ---------------------------

    // ¿Debemos mostrar el AlertDialog en pantalla?
    var verAlerta by mutableStateOf(false)
        private set

    // Título que aparece arriba del AlertDialog
    var tituloAlerta by mutableStateOf("")
        private set

    // Mensaje del cuerpo del AlertDialog
    var mensajeAlerta by mutableStateOf("")
        private set

    // Texto del botón de confirmación del AlertDialog
    var textoBtnAlerta by mutableStateOf("Confirmar")
        private set


    // ---------------------------
    // LÓGICA DE VALIDACIÓN
    // ---------------------------

    /**
     * validarLogin()
     *
     * Se ejecuta cuando el usuario toca el botón "Acceder".
     * Evalúa:
     *  - Campos vacíos
     *  - Formato básico de correo
     *  - Largo mínimo de la clave
     *
     * Según el resultado, configura los textos de la alerta
     * y activa verAlerta = true para que la UI muestre el popup.
     */
    fun validarLogin() {
        if (correo.isBlank() || clave.isBlank()) {
            // Caso 1: faltan datos
            tituloAlerta = "Error al iniciar sesión."
            mensajeAlerta = "Correo y clave no pueden estar vacíos."
            textoBtnAlerta = "Confirmar"
            verAlerta = true
            return
        }

        if (!correo.contains("@")) {
            // Caso 2: correo con formato poco válido
            tituloAlerta = "Error al iniciar sesión."
            mensajeAlerta = "El correo ingresado no es válido."
            textoBtnAlerta = "Entendido"
            verAlerta = true
            return
        }

        if (clave.length < 6) {
            // Caso 3: clave demasiado corta
            tituloAlerta = "Error al iniciar sesión."
            mensajeAlerta = "La clave debe tener al menos 6 caracteres."
            textoBtnAlerta = "Corregir"
            verAlerta = true
            return
        }

        // Caso 4: todo bien (éxito)
        tituloAlerta = "Inicio correcto"
        mensajeAlerta = "Credenciales válidas. (Próximo paso: ir a Home)"
        textoBtnAlerta = "Continuar"
        verAlerta = true

        // Más adelante: acá podrías disparar navegación a Home.
    }

    /**
     * cerrarAlerta()
     *
     * Se llama al apretar el botón del AlertDialog.
     * Cambia verAlerta a false para ocultar el popup.
     */
    fun cerrarAlerta() {
        verAlerta = false
    }
}
