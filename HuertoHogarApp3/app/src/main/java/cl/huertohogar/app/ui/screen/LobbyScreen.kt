package cl.huertohogar.app.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cl.huertohogar.app.R
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme
import cl.huertohogar.app.viewmodel.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavController) {

    val weatherViewModel = remember { WeatherViewModel() }

    val clima by weatherViewModel.clima.collectAsState()
    val isLoading by weatherViewModel.isLoading.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        weatherViewModel.cargarClima("Santiago")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Huerto Hogar")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Huerto Hogar",
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(8.dp)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp)
            ) {
                Text(
                    text = "¡Bienvenido a Huerto Hogar! 🥕",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary

                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    when {
                        isLoading -> {
                            CircularProgressIndicator()
                        }

                        error != null -> {
                            Text("No se pudo cargar el clima.", color = Color.Red)
                            Spacer(Modifier.height(8.dp))

                            Button(onClick = {
                                weatherViewModel.cargarClima("Santiago")
                            }) {
                                Text("Reintentar")
                            }
                        }

                        clima != null -> {
                            Text(
                                text = "Clima en ${clima!!.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "${clima!!.main.temp}°C",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = clima!!.weather.first().description.replaceFirstChar { it.uppercase() },
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }


            Button(
                onClick = { navController.navigate("catalogo") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Catálogo", fontSize = 20.sp) }


            Button(
                onClick = { navController.navigate("perfil") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) { Text("Mi Perfil", fontSize = 20.sp) }


            Button(
                onClick = { navController.navigate("seguimiento") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Seguimiento de Pedido", fontSize = 20.sp) }


            Button(
                onClick = { navController.navigate("nosotros") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) { Text("Sobre Nosotros", fontSize = 20.sp) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLobby() {
    HuertoHogarAppTheme {
        LobbyScreen(rememberNavController())
    }
}
