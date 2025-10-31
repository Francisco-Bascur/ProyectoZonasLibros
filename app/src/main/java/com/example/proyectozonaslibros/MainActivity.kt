package com.example.proyectozonaslibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectozonaslibros.navigations.Navigation

import com.example.proyectozonaslibros.ui.theme.ProyectoZonasLibrosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoZonasLibrosTheme {  }
            Navigation() // <-  se vizualiza  pantalla de login inicio session() // <-  se vizualiza  pantalla de login inicio session
            }
        }

    }
