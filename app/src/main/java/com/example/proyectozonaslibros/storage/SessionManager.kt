package com.example.proyectozonaslibros.storage

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun guardarUsuario(correo: String, contrasena: String) {
        val editor = prefs.edit()
        editor.putString("correo", correo)
        editor.putString("contrasena", contrasena)
        editor.apply()
    }

    fun obtenerCorreo(): String? = prefs.getString("correo", null)

    fun obtenerContrasena(): String? = prefs.getString("contrasena", null)
}
