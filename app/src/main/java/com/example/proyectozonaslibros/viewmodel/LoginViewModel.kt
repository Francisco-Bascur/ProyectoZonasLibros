package com.example.proyectozonaslibros.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.proyectozonaslibros.models.LoginModel
import com.example.proyectozonaslibros.storage.SessionManager

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    //  MODELO PRINCIPAL (correo y contraseña)
    var loginModel by mutableStateOf(LoginModel(correo = "", contrasena = ""))
        private set

    fun cambioCorreo(nuevo: String) {
        loginModel = loginModel.copy(correo = nuevo)
    }

    fun cambioContrasena(nuevo: String) {
        loginModel = loginModel.copy(contrasena = nuevo)
    }

    //  ALERTA
    var mostrarAlerta by mutableStateOf(false)
        private set
    var tituloAlerta by mutableStateOf("")
        private set
    var mensajeAlerta by mutableStateOf("")
        private set
    var textoBotonAlerta by mutableStateOf("")
        private set

    fun descartarAlerta() {
        mostrarAlerta = false
    }

    //  NAVEGACIÓN AL HOME
    var deberiamosNavegar by mutableStateOf(false)
        private set

    fun cambiarEstadoNavegacion() {
        deberiamosNavegar = false
    }

    //  PERSISTENCIA LOCAL
    private val sessionManager = SessionManager(application)

    // LÓGICA DE LOGIN
    fun auth() {
        val correo = loginModel.correo.trim()
        val clave = loginModel.contrasena

        //  Validar vacíos
        if (correo.isBlank() || clave.isBlank()) {
            mostrarError(
                titulo = "Campos vacíos",
                mensaje = "El correo y la contraseña no pueden estar vacíos."
            )
            return
        }
        //  Obtener datos guardados
        val correoGuardado = sessionManager.obtenerCorreo()
        val claveGuardada = sessionManager.obtenerContrasena()


        //  Comparar credenciales
        if (correo != correoGuardado || clave != claveGuardada) {
            mostrarError(
                titulo = "Credenciales incorrectas",
                mensaje = "El correo o la contraseña no corresponden."
            )
            return
        }

        // LOGIN exitoso alerta inicio  navegación
        tituloAlerta = "Bienvenido"
        mensajeAlerta = "Inicio de sesión exitoso."
        textoBotonAlerta = "Continuar"
        mostrarAlerta = true
        deberiamosNavegar = true
    }

    private fun mostrarError(titulo: String, mensaje: String) {
        tituloAlerta = titulo
        mensajeAlerta = mensaje
        textoBotonAlerta = "Aceptar"
        mostrarAlerta = true
        deberiamosNavegar = false
    }
}