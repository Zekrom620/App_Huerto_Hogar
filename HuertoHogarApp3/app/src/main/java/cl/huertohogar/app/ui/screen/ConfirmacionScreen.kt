package cl.huertohogar.app.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.huertohogar.app.navigation.Destinos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(navController: NavController) {

    var visible by remember { mutableStateOf(true) }

    val iconScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 600)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Confirmación de Pedido") })
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )

            Spacer(Modifier.height(16.dp))

            Text("¡Gracias por tu compra!", fontSize = 26.sp)

            Spacer(Modifier.height(8.dp))

            Text(
                "Tu pedido ha sido procesado exitosamente.",
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(Destinos.Catalogo.ruta) },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Volver al catálogo")
            }
        }
    }
}
