package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme

data class ProductoCarrito(
    val id: Int,
    val nombre: String,
    val precio: String,
    val cantidad: Int,
    val imagen: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController) {
    // Carrito como estado mutable para simular vaciado
    var productosEnCarrito by remember {
        mutableStateOf(
            listOf(
                ProductoCarrito(1, "Zanahorias Orgánicas", "$1.500 / kg", 2, R.drawable.ic_plantita),
                ProductoCarrito(2, "Tomates Cherry", "$2.000 / kg", 1, R.drawable.ic_plantita)
            )
        )
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (productosEnCarrito.isEmpty()) {
                // Mensaje cuando el carrito está vacío
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tu carrito está vacío.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productosEnCarrito) { producto ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = producto.imagen),
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Cantidad: ${producto.cantidad}")
                                Text(producto.precio, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Vaciar carrito y navegar a la pantalla de confirmación
                        productosEnCarrito = emptyList()
                        navController.navigate(Destinos.Confirmacion.ruta)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar Compra")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCarritoScreen() {
    HuertoHogarAppTheme {
        CarritoScreen(navController = rememberNavController())
    }
}
