package com.example.proyectozonaslibros

import com.example.proyectozonaslibros.data.remote.LibroApiService
import com.example.proyectozonaslibros.models.Libro

class FakeLibroApiService : LibroApiService {

    private val libros = mutableListOf(
        Libro(1, "Libro 1", "Autor 1", "Cat 1"),
        Libro(2, "Libro 2", "Autor 2", "Cat 2")
    )

    override suspend fun obtenerLibros(): List<Libro> {
        return libros.toList()
    }

    override suspend fun crearLibro(libro: Libro): Libro {
        val nuevoId = (libros.maxOfOrNull { it.id } ?: 0) + 1
        val creado = libro.copy(id = nuevoId)
        libros.add(creado)
        return creado
    }

    override suspend fun actualizarLibro(id: Int, libro: Libro): Libro {
        val index = libros.indexOfFirst { it.id == id }
        if (index != -1) {
            val actualizado = libro.copy(id = id)
            libros[index] = actualizado
            return actualizado
        }
        return libro
    }

    override suspend fun eliminarLibro(id: Int) {
        libros.removeAll { it.id == id }
    }
}
