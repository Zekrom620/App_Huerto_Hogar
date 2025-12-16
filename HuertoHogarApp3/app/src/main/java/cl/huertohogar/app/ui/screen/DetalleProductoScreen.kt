package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.viewmodel.CarritoViewModel
import cl.huertohogar.app.viewmodel.ProductViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    carritoViewModel: CarritoViewModel,
    idProducto: Long
) {

    val producto by viewModel.productoDetalle.collectAsState()
    val isLoading by viewModel.isLoadingDetalle.collectAsState()
    val errorMessage by viewModel.errorDetalle.collectAsState()

    var cantidad by remember { mutableStateOf(1) }

    LaunchedEffect(idProducto) {
        viewModel.cargarProductoPorId(idProducto)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                producto != null -> {

                    val p = producto!!

                    AsyncImage(
                        model = "http://192.168.1.4:8080/${p.imagen}",
                        contentDescription = p.nombre,
                        modifier = Modifier.size(240.dp).clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_plantita)
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(p.nombre, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text(p.descripcion, textAlign = androidx.compose.ui.text.style.TextAlign.Center)

                    Spacer(Modifier.height(12.dp))
                    Text("Precio: $${p.precio}", fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Stock disponible: ${p.stock}")

                    Spacer(Modifier.height(16.dp))

                    // 🔢 SELECTOR DE CANTIDAD
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { if (cantidad > 1) cantidad-- },
                            enabled = cantidad > 1
                        ) { Text("-") }

                        Text(
                            cantidad.toString(),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 20.sp
                        )

                        Button(
                            onClick = { if (cantidad < p.stock) cantidad++ },
                            enabled = cantidad < p.stock
                        ) { Text("+") }
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(p, cantidad)
                            navController.navigate("carrito")
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        enabled = p.stock > 0
                    ) {
                        Text("Agregar al carrito", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
