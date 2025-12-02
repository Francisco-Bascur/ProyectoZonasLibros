package com.example.proyectozonaslibros.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectozonaslibros.viewmodel.LibroViewModel

@Composable
fun AgregarLibroScreen(
    navController: NavHostController,
    libroViewModel: LibroViewModel
) {
    val state = libroViewModel.state   // ✅ sin errores de “it”

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(text = "Agregar libro")

        Spacer(modifier = Modifier.height(16.dp))

        // ---- TÍTULO ----
        OutlinedTextField(
            value = state.titulo,
            onValueChange = { nuevo -> libroViewModel.cambiarTitulo(nuevo) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- AUTOR ----
        OutlinedTextField(
            value = state.autor,
            onValueChange = { nuevo -> libroViewModel.cambiarAutor(nuevo) },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- CATEGORÍA ----
        OutlinedTextField(
            value = state.categoria,
            onValueChange = { nuevo -> libroViewModel.cambiarCategoria(nuevo) },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                libroViewModel.agregarLibro()
                navController.popBackStack()   // vuelve al Home
            },
            enabled = state.titulo.isNotBlank()
                    && state.autor.isNotBlank()
                    && state.categoria.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}