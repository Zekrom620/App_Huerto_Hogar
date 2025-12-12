package cl.huertohogar.app.ui.screen


import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import kotlinx.coroutines.tasks.await


private val AlwaysConsumeNestedScrollConnection = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset = available
    override suspend fun onPreFling(available: Velocity): Velocity = available
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun NosotrosScreen(navController: NavController) {


    val contexto = LocalContext.current
    val fusedLocationCliente = remember {
        LocationServices.getFusedLocationProviderClient(contexto)
    }
    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Buscando Ubicación...") }


    suspend fun recuperarCurrentPosition(){
        try {
            val location = fusedLocationCliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).await()
            if (location != null){
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Ubicación recuperada"
            }else{
                locationMessage = "No se pudo recuperar la ubicación."
            }
        }catch (e: Exception){
            locationMessage = "Error: ${e.message}"
        }
    }


    val locationPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            locationMessage = if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){
                "Permisos concedidos. Cargando ubicación..."
            } else {
                "Permisos de ubicación denegados."
            }
        }
    )


    LaunchedEffect(Unit) {
        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    LaunchedEffect(locationMessage) {
        if (locationMessage.contains("Permisos concedidos") ||
            locationMessage.contains("Ubicación recuperada")){
            recuperarCurrentPosition()
        }
    }
    // -----------------------------------------------------------------

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre Nosotros") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_person_placeholder),
                contentDescription = "Logo Huerto Hogar",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )

            Text(
                text = "Huerto Hogar",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Llevamos la frescura del campo directamente a tu mesa. Somos una empresa comprometida con la agricultura local, apoyando a productores y entregando alimentos saludables a cada hogar de Chile.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nuestra misión",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Promover un estilo de vida saludable y sostenible mediante el consumo responsable de productos naturales y frescos.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contáctanos",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "📍 Santiago, Chile\n📞 +56 9 1234 5678\n✉ contacto@huertohogar.cl",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 4.dp)
            )



            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nuestra Ubicación Central",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = locationMessage,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            userLocation?.let { (latitude, longitude) ->
                MapboxMap(
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .nestedScroll(AlwaysConsumeNestedScrollConnection),
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(16.0)
                            center(Point.fromLngLat(longitude, latitude))
                            pitch(0.0)
                            bearing(0.0)
                        }
                    },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    locationPermission.launch(
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar Permisos / Actualizar Ubicación")
            }

            Spacer(modifier = Modifier.height(30.dp)) // Espacio adicional al final
        }
    }
}