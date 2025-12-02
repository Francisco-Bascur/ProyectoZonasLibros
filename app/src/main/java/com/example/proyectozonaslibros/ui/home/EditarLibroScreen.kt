package com.example.proyectozonaslibros.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectozonaslibros.viewmodel.LibroViewModel

@Composable
fun EditarLibroScreen(
    navController: NavHostController,
    libroViewModel: LibroViewModel = viewModel()
) {
    val state = libroViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(text = "Editar libro", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.titulo,
            onValueChange = { libroViewModel.cambiarTitulo(it) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.autor,
            onValueChange = { libroViewModel.cambiarAutor(it) },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.categoria,
            onValueChange = { libroViewModel.cambiarCategoria(it) },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                libroViewModel.actualizarLibro()
                navController.popBackStack() // volver al Home
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}