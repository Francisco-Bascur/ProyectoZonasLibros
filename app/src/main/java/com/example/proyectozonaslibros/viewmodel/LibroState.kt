package com.example.proyectozonaslibros.viewmodel

import com.example.proyectozonaslibros.models.Libro


data class LibroState(
    val libros: List<Libro> = emptyList(),
    val titulo: String = "",
    val autor: String = "",
    val categoria: String = "",
    val idSeleccionado: Int? = null
)