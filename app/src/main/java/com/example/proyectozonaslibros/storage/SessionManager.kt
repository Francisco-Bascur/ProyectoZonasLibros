package com.example.proyectozonaslibros.storage

import android.content.Context
import android.content.SharedPreferences

// Clase encargada de manejar la persistencia local del usuario.
// usamos SharedPreferences para guardar y obtener datos sencillos como
// el correo y la contraseña. Esto permite que la app recuerde el login.
class SessionManager(context: Context) {
    //  Archivo SharedPreferences donde se guardará la sesión del usuario.
    // MODE_PRIVATE → solo esta app puede acceder al archivo.
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)



    // Guarda los datos del usuario en persistencia local.
    // lo  usamos cuando el usuario se registra correctamente.
    // Los valores quedan almacenados incluso si se cierra la app.
    fun guardarUsuario(correo: String, contrasena: String) {
        val editor = prefs.edit()
        editor.putString("correo", correo)
        editor.putString("contrasena", contrasena)
        editor.apply()
    }
//------------------------------------------------------------------
     //  Obtiene el correo guardado.
    // Si no existe, devuelve null.
    // Se utiliza en LoginViewModel para validar las credenciales
    fun obtenerCorreo(): String? = prefs.getString("correo", null)

    fun obtenerContrasena(): String? = prefs.getString("contrasena", null)
// Obtiene la contraseña guardada.
// Si no existe, devuelve null.
// También se usa en LoginViewModel.

}
