package com.example.proyectozonaslibros.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun HomeScreen(
    userEmail: String,
    onLogout: () -> Unit // <- se  recibe desde navegación
) {

    // animación de aparición del saludo
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 6000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBBDEFB), // azul clarito
                        Color(0xFFE3F2FD), // celeste suave
                        Color(0xFFFFFFFF)  // blanco
                    )
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ----- saludo -----
        Text(
            text = "Bienvenido 👋",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(alphaAnim)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Has iniciado sesión como:\n$userEmail",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ----- acciones rápidas -----
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimpleActionCard("Mis libros")
            SimpleActionCard("Buscar")
            SimpleActionCard("Favoritos")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ----- recomendados -----
        Text(
            text = "Recomendados para ti",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BookCardPreview(
                title = "El Quijote",
                author = "M. de Cervantes",
                bg = Color(0xFF5C6BC0)
            )
            BookCardPreview(
                title = "Cien Años de Soledad",
                author = "G. García Márquez",
                bg = Color(0xFF3949AB)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ----- botón cerrar sesión -----
        Button(
            onClick = { onLogout() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3949AB),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Cerrar sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// tarjeta pequeña con texto (acciones rápidas)
@Composable
fun SimpleActionCard(label: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF3949AB),
                textAlign = TextAlign.Center
            )
        }
    }
}

// tarjeta "libro recomendado"
@Composable
fun BookCardPreview(
    title: String,
    author: String,
    bg: Color
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = bg
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // emoji libro para simular portada
            Text(
                text = "📚",
                fontSize = 28.sp
            )
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = author,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

