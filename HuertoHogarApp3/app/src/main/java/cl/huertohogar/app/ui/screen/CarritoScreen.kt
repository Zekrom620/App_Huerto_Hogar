package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.utils.SessionManager
import cl.huertohogar.app.viewmodel.CarritoViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {

    val items by carritoViewModel.carrito.collectAsState()
    val isLoading by carritoViewModel.isLoading.collectAsState()
    val errorMensaje by carritoViewModel.errorMensaje.collectAsState()
    val compraExitosa by carritoViewModel.compraExitosa.collectAsState()

    val userId = SessionManager.userId

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
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

            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Tu carrito está vacío.")
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->

                    val imageUrl =
                        "http://192.168.1.4:8080/${item.producto.imagen}"

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AsyncImage(
                                model = imageUrl,
                                contentDescription = item.producto.nombre,
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.ic_plantita),
                                error = painterResource(R.drawable.ic_plantita)
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    item.producto.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Cantidad: ${item.cantidad}")
                                Text("Subtotal: $${item.subtotal()}")
                            }

                            IconButton(
                                onClick = {
                                    carritoViewModel.eliminarProducto(item.producto.id!!)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Total: $${carritoViewModel.totalCarrito()}",
                style = MaterialTheme.typography.titleLarge
            )

            if (errorMensaje != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    errorMensaje!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Destinos.Catalogo.ruta)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Seguir comprando")
            }

            Spacer(Modifier.height(8.dp))

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
