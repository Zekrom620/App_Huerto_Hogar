package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cl.huertohogar.app.R
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme
import cl.huertohogar.app.model.Producto // <<-- Importación del modelo
import cl.huertohogar.app.model.listaProductos // <<-- Importación de la lista

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(navController: NavController, codigo: String) {


    val producto = listaProductos.find { it.codigo == codigo } ?: Producto(
        codigo = "N/A",
        nombre = "Producto no encontrado",
        precio = "N/A",
        stock = "N/A",
        descripcion = "No se pudo cargar la información del producto con código $codigo.",
        imagen = R.drawable.ic_plantita
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(producto.nombre) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
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
            Image(
                painter = painterResource(id = producto.imagen), // Usar la imagen real
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = producto.nombre, // Usar el nombre real
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.descripcion, // Usar la descripción real
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = producto.stock, // Mostrar el stock
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.precio, // Usar el precio real
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Aquí más adelante agregaremos la función agregar al carrito */ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Agregar al carrito",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalleProductoScreenPreview() {
    val navController = rememberNavController()
    HuertoHogarAppTheme {
        DetalleProductoScreen(navController, codigo = "VR001")
    }
}