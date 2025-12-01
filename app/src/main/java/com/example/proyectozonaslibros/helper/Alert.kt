package com.example.proyectozonaslibros.helper



import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowAlert(
    titulo: String,
    mensaje: String,
    textoBtnConfirmar: String = "Aceptar",
    onConfirm: () -> Unit,

) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text(text = titulo) },
        text = { Text(text = mensaje) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = textoBtnConfirmar)
            }
        },



    )
}
