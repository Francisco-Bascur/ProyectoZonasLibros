package com.example.proyectozonaslibros.data.remote

import com.example.proyectozonaslibros.models.Libro
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LibroApiService {

    @GET("libros")
    suspend fun obtenerLibros(): List<Libro>

    @POST("libros")
    suspend fun crearLibro(@Body libro: Libro): Libro

    @PUT("libros/{id}")
    suspend fun actualizarLibro(
        @Path("id") id: Int,
        @Body libro: Libro
    ): Libro

    @DELETE("libros/{id}")
    suspend fun eliminarLibro(
        @Path("id") id: Int
    )
}