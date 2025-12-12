package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme
import cl.huertohogar.app.model.Producto
import cl.huertohogar.app.model.listaProductos
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController) {

    val categorias = listOf(
        "Frutas Frescas" to "Nuestra selección de frutas frescas ofrece una experiencia directa del campo a tu hogar. Cultivadas y cosechadas en el punto óptimo de madurez, aportan sabor, vitaminas y frescura a tu dieta diaria.",
        "Verduras Orgánicas" to "Verduras cultivadas sin pesticidas ni químicos, garantizando un sabor auténtico y natural. Ricas en vitaminas y fibra, ideales para una alimentación saludable.",
        "Productos Orgánicos" to "Elaborados con ingredientes naturales y procesados de forma responsable. Desde miel hasta quinua, estos productos promueven un estilo de vida saludable y sostenible.",
        "Productos Lácteos" to "Provenientes de granjas locales, nuestros lácteos ofrecen frescura, sabor auténtico y una excelente fuente de calcio y nutrientes esenciales."
    )

    val productos = listaProductos


    val productosPorCategoria = remember {
        mapOf(
            "Frutas Frescas" to productos.filter { it.codigo.startsWith("FR") },
            "Verduras Orgánicas" to productos.filter { it.codigo.startsWith("VR") },
            "Productos Orgánicos" to productos.filter { it.codigo.startsWith("PO") },
            "Productos Lácteos" to productos.filter { it.codigo.startsWith("PL") }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo HuertoHogar", fontWeight = FontWeight.Bold) },
                // NUEVO BOTÓN PARA VOLVER AL LOBBY
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("lobby") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al Lobby",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destinos.Carrito.ruta) }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Ir al carrito",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destinos.Carrito.ruta) },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Ver carrito",
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                categorias.forEach { (nombre, descripcion) ->
                    item {
                        CategoriaCard(nombre, descripcion)
                    }
                    val lista = productosPorCategoria[nombre].orEmpty()
                    items(lista) { producto ->
                        ProductoCard(producto = producto) {
                            navController.navigate("detalleProducto/${producto.codigo}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaCard(nombre: String, descripcion: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                nombre,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                descripcion,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun ProductoCard(producto: Producto, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(producto.precio, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                Text(producto.stock, fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
            }

            ElevatedButton(
                onClick = onDetailClick,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Ver Detalle",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCatalogoScreen() {
    HuertoHogarAppTheme {
        CatalogoScreen(rememberNavController())
    }
}