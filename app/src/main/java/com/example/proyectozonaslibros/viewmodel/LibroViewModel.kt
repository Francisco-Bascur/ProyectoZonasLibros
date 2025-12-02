package com.example.proyectozonaslibros.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectozonaslibros.models.Libro
import com.example.proyectozonaslibros.models.LibroState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibroViewModel : ViewModel() {

    // Estado único para Home / Agregar / Editar
    var state by mutableStateOf(LibroState())
        private set

    init {
        obtenerLibros()
    }

    // ---- CAMPOS DEL FORM ----
    fun cambiarTitulo(nuevo: String) {
        state = state.copy(titulo = nuevo)
    }

    fun cambiarAutor(nuevo: String) {
        state = state.copy(autor = nuevo)
    }

    fun cambiarCategoria(nuevo: String) {
        state = state.copy(categoria = nuevo)
    }

    // ---- SIMULACIÓN DE API (lista inicial) ----
    fun obtenerLibros() {
        viewModelScope.launch {
            delay(300) // pequeña espera visual

            val librosDemo = listOf(
                Libro(1, "Cien años de soledad", "Gabriel García Márquez", "Novela"),
                Libro(2, "El principito", "Antoine de Saint-Exupéry", "Infantil"),
                Libro(3, "Clean Code", "Robert C. Martin", "Tecnología"),
                Libro(4, "Kotlin in Action", "Dmitry Jemerov", "Programación")
            )

            state = state.copy(libros = librosDemo)
        }
    }

    // ---- AGREGAR LIBRO ----
    fun agregarLibro() {
        if (state.titulo.isBlank() || state.autor.isBlank() || state.categoria.isBlank()) return

        val nuevoId = (state.libros.maxOfOrNull { it.id } ?: 0) + 1

        val nuevoLibro = Libro(
            id = nuevoId,
            titulo = state.titulo,
            autor = state.autor,
            categoria = state.categoria
        )

        state = state.copy(
            libros = state.libros + nuevoLibro,
            titulo = "",
            autor = "",
            categoria = "",
            libroSeleccionadoId = null
        )
    }

    // ---- SELECCIONAR LIBRO PARA EDITAR ----
    fun seleccionarLibroParaEditar(libro: Libro) {
        state = state.copy(
            libroSeleccionadoId = libro.id,
            titulo = libro.titulo,
            autor = libro.autor,
            categoria = libro.categoria
        )
    }

    // ---- ACTUALIZAR LIBRO ----
    fun actualizarLibro() {
        val id = state.libroSeleccionadoId ?: return

        if (state.titulo.isBlank() || state.autor.isBlank() || state.categoria.isBlank()) return

        val listaActualizada = state.libros.map { libro ->
            if (libro.id == id) {
                libro.copy(
                    titulo = state.titulo,
                    autor = state.autor,
                    categoria = state.categoria
                )
            } else libro
        }

        state = state.copy(
            libros = listaActualizada,
            titulo = "",
            autor = "",
            categoria = "",
            libroSeleccionadoId = null
        )
    }

    // ---- ELIMINAR LIBRO ----
    fun eliminarLibro(id: Int) {
        val listaNueva = state.libros.filterNot { it.id == id }
        state = state.copy(libros = listaNueva)
    }
}