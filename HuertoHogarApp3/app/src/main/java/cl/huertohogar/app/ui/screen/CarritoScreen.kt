package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.viewmodel.CarritoViewModel
import cl.huertohogar.app.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    loginViewModel: LoginViewModel
) {

    val productos by carritoViewModel.carrito.collectAsState()
    val isLoading by carritoViewModel.isLoading.collectAsState()
    val errorMensaje by carritoViewModel.errorMensaje.collectAsState()
    val compraExitosa by carritoViewModel.compraExitosa.collectAsState()

    val userId = loginViewModel.uiState.userId

    LaunchedEffect(compraExitosa) {
        if (compraExitosa) {

            carritoViewModel.limpiarCarrito()

            navController.navigate(Destinos.Confirmacion.ruta) {
                popUpTo(Destinos.Carrito.ruta) { inclusive = true }
            }

            carritoViewModel.resetearCompraExitosa()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (productos.isEmpty()) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío.")
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productos) { p ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_plantita),
                            contentDescription = p.nombre,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("Precio: $${p.precio}")
                        }
                    }

                    Divider()
                }
            }

            Text(
                text = "Total: $${carritoViewModel.totalCarrito()}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (errorMensaje != null) {
                Text(
                    errorMensaje!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Button(
                onClick = {
                    if (userId != null && !isLoading) {
                        carritoViewModel.enviarBoleta(userId)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Finalizar Compra")
                }
            }
        }
    }
}
