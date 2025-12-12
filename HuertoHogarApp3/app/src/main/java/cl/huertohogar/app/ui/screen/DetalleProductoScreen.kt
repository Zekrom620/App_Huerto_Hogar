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
import cl.huertohogar.app.viewmodel.ProductViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    idProducto: Long
) {
    val producto by viewModel.productoDetalle.collectAsState()
    val isLoading by viewModel.isLoadingDetalle.collectAsState()
    val errorMessage by viewModel.errorDetalle.collectAsState()

    LaunchedEffect(idProducto) {
        viewModel.cargarProductoPorId(idProducto)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
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
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when {
                isLoading -> {
                    Spacer(Modifier.height(200.dp))
                    CircularProgressIndicator()
                }

                errorMessage != null -> {
                    Spacer(Modifier.height(100.dp))
                    Text(
                        text = errorMessage ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp
                    )
                }

                producto == null -> {
                    Spacer(Modifier.height(100.dp))
                    Text("Producto no encontrado.", fontSize = 18.sp)
                }

                else -> {
                    DetalleProductoContent(
                        producto = producto!!,
                        onAgregarCarrito = {
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetalleProductoContent(
    producto: Product,
    onAgregarCarrito: () -> Unit
) {
    val imageUrl = "http://192.168.1.4:8080/${producto.imagen}"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        AsyncImage(
            model = imageUrl,
            contentDescription = producto.nombre,
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_plantita),
            error = painterResource(id = R.drawable.ic_plantita)
        )

        Spacer(Modifier.height(16.dp))

        Text(producto.nombre, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(Modifier.height(8.dp))

        Text(
            producto.descripcion,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Stock: ${producto.stock}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "$${producto.precio}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onAgregarCarrito,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Agregar al carrito", fontSize = 18.sp)
        }
    }
}
