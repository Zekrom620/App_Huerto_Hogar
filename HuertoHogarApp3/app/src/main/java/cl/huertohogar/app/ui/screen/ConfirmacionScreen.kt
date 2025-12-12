package cl.huertohogar.app.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(
    navController: NavController,
    previewMode: Boolean = false
) {
    var visible by remember { mutableStateOf(previewMode) }


    val iconScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.6f,
        animationSpec = tween(durationMillis = 600)
    )


    if (!previewMode) {
        LaunchedEffect(Unit) {
            delay(250)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmación de Pedido") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Pedido confirmado",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Gracias por tu compra!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu pedido ha sido confirmado exitosamente y pronto será enviado.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(Destinos.Catalogo.ruta) },
                modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Volver al inicio",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver al catálogo", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewConfirmacionScreen() {
    HuertoHogarAppTheme {
        ConfirmacionScreen(navController = rememberNavController(), previewMode = true)
    }
}
