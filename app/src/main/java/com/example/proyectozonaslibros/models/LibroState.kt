package com.example.proyectozonaslibros.models
data class LibroState(
    val titulo: String = "",
    val autor: String = "",
    val categoria: String = "",

    // lista que se muestra en Home
    val libros: List<Libro> = emptyList(),

    // id del libro que estamos editando (si corresponde)
    val libroSeleccionadoId: Int? = null
)