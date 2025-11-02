package com.example.proyectozonaslibros.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// ViewModel encargado de manejar los estados y validaciones del registro
class RegisterViewModel : ViewModel() {

    // Campos del formulario
    var nombre = mutableStateOf("")
    var correo = mutableStateOf("")
    var clave = mutableStateOf("")
    var confirmarClave = mutableStateOf("")

    // Mensaje de error que se mostrará en pantalla si algo está mal
    var errorMensaje = mutableStateOf("")

    // Función que valida los datos del formulario
    fun validarRegistro(): Boolean {

        // Validar que ningún campo esté vacío
        if (nombre.value.isBlank() || correo.value.isBlank() ||
            clave.value.isBlank() || confirmarClave.value.isBlank()) {
            errorMensaje.value = "Todos los campos son obligatorios"
            return false
        }

        // Validar que las contraseñas coincidan
        if (clave.value != confirmarClave.value) {
            errorMensaje.value = "Las contraseñas no coinciden"
            return false
        }

        // Si todo está correcto, limpiar errores
        errorMensaje.value = ""
        return true
    }
}

