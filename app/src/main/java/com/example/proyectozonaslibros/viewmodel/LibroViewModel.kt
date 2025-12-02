package com.example.proyectozonaslibros.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectozonaslibros.models.Libro
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LibroViewModel : ViewModel() {

    var state = LibroState()
        private set

    init {
        obtenerLibros()
    }

    fun cambiarTitulo(nuevo: String) {
        state = state.copy(titulo = nuevo)
    }

    fun cambiarAutor(nuevo: String) {
        state = state.copy(autor = nuevo)
    }

    fun cambiarCategoria(nuevo: String) {
        state = state.copy(categoria = nuevo)
    }

    // --- SIMULACIÓN DE API (para pruebas visuales) ---
    fun obtenerLibros() {
        viewModelScope.launch {
            delay(500) // simula carga
            state = state.copy(
                libros = listOf(
                    Libro(1, "Cien años de soledad", "Gabriel García Márquez", "Novela"),
                    Libro(2, "El principito", "Antoine de Saint-Exupéry", "Infantil"),
                    Libro(3, "Clean Code", "Robert C. Martin", "Tecnología"),
                    Libro(4, "Kotlin in Action", "Dmitry Jemerov", "Programación")
                )
            )
        }
    }
}