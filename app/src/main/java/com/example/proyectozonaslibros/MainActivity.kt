package com.example.proyectozonaslibros


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.proyectozonaslibros.navigations.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                                 // Se inicia la navegación principal       Login - register - home
                Navigation()
            }
        }
    }
}
