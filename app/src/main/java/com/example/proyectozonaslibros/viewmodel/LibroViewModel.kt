package com.example.proyectozonaslibros.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectozonaslibros.data.remote.LibroApiService
import com.example.proyectozonaslibros.data.remote.RetrofitInstance
import com.example.proyectozonaslibros.models.Libro
import com.example.proyectozonaslibros.models.LibroState
import kotlinx.coroutines.launch

class LibroViewModel(
    private val api: LibroApiService = RetrofitInstance.api
) : ViewModel() {

    var state by mutableStateOf(LibroState())
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

    // ---------------- GET /libros ----------------
    fun obtenerLibros() {
        viewModelScope.launch {
            try {
                val librosRemotos = api.obtenerLibros()
                state = state.copy(libros = librosRemotos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ---------------- POST /libros ----------------
    fun agregarLibro() {
        if (state.titulo.isBlank() || state.autor.isBlank() || state.categoria.isBlank()) return

        val nuevoLibro = Libro(
            id = 0,
            titulo = state.titulo,
            autor = state.autor,
            categoria = state.categoria
        )

        viewModelScope.launch {
            try {
                api.crearLibro(nuevoLibro)

                val librosActualizados = api.obtenerLibros()
                state = state.copy(
                    libros = librosActualizados,
                    titulo = "",
                    autor = "",
                    categoria = "",
                    libroSeleccionadoId = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ---------------- EDITAR (PUT) ----------------
    fun seleccionarLibroParaEditar(libro: Libro) {
        state = state.copy(
            libroSeleccionadoId = libro.id,
            titulo = libro.titulo,
            autor = libro.autor,
            categoria = libro.categoria
        )
    }

    fun actualizarLibro() {
        val id = state.libroSeleccionadoId ?: return
        if (state.titulo.isBlank() || state.autor.isBlank() || state.categoria.isBlank()) return

        viewModelScope.launch {
            try {
                val libroActualizado = Libro(
                    id = id,
                    titulo = state.titulo,
                    autor = state.autor,
                    categoria = state.categoria
                )

                val actualizadoRemoto = api.actualizarLibro(id, libroActualizado)

                state = state.copy(
                    libros = state.libros.map { libro ->
                        if (libro.id == id) actualizadoRemoto else libro
                    },
                    titulo = "",
                    autor = "",
                    categoria = "",
                    libroSeleccionadoId = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ---------------- DELETE ----------------
    fun eliminarLibro(id: Int) {
        viewModelScope.launch {
            try {
                api.eliminarLibro(id)
                state = state.copy(libros = state.libros.filterNot { it.id == id })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}