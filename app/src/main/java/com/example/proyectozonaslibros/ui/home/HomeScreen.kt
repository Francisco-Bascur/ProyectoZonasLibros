package com.example.proyectozonaslibros.ui.home



import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(userEmail: String) {

    // Estado para controlar la animación
    var visible by remember { mutableStateOf(false) }

    // Lanzamos la animación cuando se abre la pantalla
    LaunchedEffect(Unit) {
        delay(300) // pequeña espera para suavidad
        visible = true
    }

    // Animamos la opacidad del texto
    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 6000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBBDEFB), // Azul claro
                        Color(0xFFE3F2FD), // Celeste pálido
                        Color(0xFFFFFFFF) // blanco
                    )
                )
            )
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido ! ",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(alphaAnim) // 👈 animación aplicada
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has iniciado sesión como:\n$userEmail",
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}


