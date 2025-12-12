package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.viewmodel.CarritoViewModel
import cl.huertohogar.app.viewmodel.ProductViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    carritoViewModel: CarritoViewModel
) {

    val productos by viewModel.productos.collectAsState()
    val isLoading by viewModel.isLoadingLista.collectAsState()
    val errorMessage by viewModel.errorLista.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo HuertoHogar", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("lobby") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destinos.Carrito.ruta) }) {
                        Icon(Icons.Filled.ShoppingCart, "Carrito",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(padding)
        ) {

            when {
                isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }

                errorMessage != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(errorMessage ?: "Error", color = Color.Red)
                }

                productos.isEmpty() -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("No hay productos disponibles")
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(productos) { producto ->
                            ProductoCardDentro(
                                producto = producto,
                                onDetailClick = {
                                    navController.navigate("detalleProducto/${producto.id}")
                                },
                                onAddToCart = {
                                    carritoViewModel.agregarProducto(producto)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCardDentro(
    producto: Product,
    onDetailClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val imagenUrl = "http://192.168.1.4:8080/uploads/${producto.imagen}"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = "http://192.168.1.4:8080/${producto.imagen}",
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_plantita),
                error = painterResource(id = R.drawable.ic_plantita)
            )


            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold)
                Text("Precio: $${producto.precio}", color = MaterialTheme.colorScheme.primary)
                Text("Stock: ${producto.stock}")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Button(onClick = onDetailClick) {
                    Text("Ver")
                }

                Spacer(Modifier.height(6.dp))

                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Agregar")
                }
            }
        }
    }
}
