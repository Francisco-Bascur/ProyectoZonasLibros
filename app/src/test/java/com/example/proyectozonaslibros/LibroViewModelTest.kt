package com.example.proyectozonaslibros

import com.example.proyectozonaslibros.viewmodel.LibroViewModel
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LibroViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun obtenerLibros_cargaListaInicial() = runTest {
        val fakeApi = FakeLibroApiService()
        val viewModel = LibroViewModel(api = fakeApi)

        advanceUntilIdle()

        assertEquals(2, viewModel.state.libros.size)
    }

    @Test
    fun agregarLibro_funcionaCorrectamente() = runTest {
        val fakeApi = FakeLibroApiService()
        val viewModel = LibroViewModel(api = fakeApi)

        advanceUntilIdle()

        viewModel.cambiarTitulo("Nuevo Test")
        viewModel.cambiarAutor("Autora Test")
        viewModel.cambiarCategoria("Prueba")

        viewModel.agregarLibro()
        advanceUntilIdle()

        assertEquals(3, viewModel.state.libros.size)
        assertEquals("Nuevo Test", viewModel.state.libros.last().titulo)
    }
    @Test
    fun agregarLibro_conCamposVacios_noAgrega() = runTest {
        val fakeApi = FakeLibroApiService()
        val viewModel = LibroViewModel(api = fakeApi)

        advanceUntilIdle() // carga inicial: 2 libros
        val tamañoAntes = viewModel.state.libros.size

        // NO llenamos los campos → se quedan vacíos
        viewModel.agregarLibro()
        advanceUntilIdle()

        // Como los campos están vacíos, no debería cambiar la lista
        assertEquals(tamañoAntes, viewModel.state.libros.size)
    }

    @Test
    fun eliminarLibro_eliminaLibroDeLaLista() = runTest {
        val fakeApi = FakeLibroApiService()
        val viewModel = LibroViewModel(api = fakeApi)

        advanceUntilIdle() // 2 libros: id 1 y 2

        viewModel.eliminarLibro(1)
        advanceUntilIdle()

        // Queda solo 1 libro y ninguno tiene id 1
        assertEquals(1, viewModel.state.libros.size)
        assertEquals(false, viewModel.state.libros.any { it.id == 1 })
    }

    @Test
    fun actualizarLibro_modificaTituloDelLibro() = runTest {
        val fakeApi = FakeLibroApiService()
        val viewModel = LibroViewModel(api = fakeApi)

        advanceUntilIdle()

        // Tomamos el primer libro, lo seleccionamos para edición
        val libroOriginal = viewModel.state.libros.first()
        viewModel.seleccionarLibroParaEditar(libroOriginal)

        // Cambiamos campos como si el usuario editara
        viewModel.cambiarTitulo("Titulo Editado")
        viewModel.cambiarAutor(libroOriginal.autor)
        viewModel.cambiarCategoria(libroOriginal.categoria)

        viewModel.actualizarLibro()
        advanceUntilIdle()

        val libroEditado = viewModel.state.libros.first { it.id == libroOriginal.id }
        assertEquals("Titulo Editado", libroEditado.titulo)
    }

}
