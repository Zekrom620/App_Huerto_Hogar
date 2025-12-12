package cl.huertohogar.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.viewmodel.RegistroFormEvent
import cl.huertohogar.app.viewmodel.RegistroViewModel
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme

@Composable
fun RegistroScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    val uiState = viewModel.uiState

    var scale by remember { mutableStateOf(0.9f) }
    LaunchedEffect(Unit) {
        delay(100)
        scale = 1f
    }

    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            delay(800)
            navController.navigate(Destinos.Login.ruta) {
                popUpTo(Destinos.Registro.ruta) { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF4CAF50), Color(0xFFA5D6A7))
                )
            ),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Crear Cuenta 🌱",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Nombre(it)) },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                isError = uiState.nombreError != null,
                singleLine = true
            )
            if (uiState.nombreError != null) {
                Text(uiState.nombreError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Correo(it)) },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                isError = uiState.correoError != null,
                singleLine = true
            )
            if (uiState.correoError != null) {
                Text(uiState.correoError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.rut,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Rut(it)) },
                label = { Text("RUT") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                isError = uiState.rutError != null,
                singleLine = true
            )
            if (uiState.rutError != null) {
                Text(uiState.rutError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Direccion(it)) },
                label = { Text("Dirección") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                isError = uiState.direccionError != null,
                singleLine = true
            )
            if (uiState.direccionError != null) {
                Text(uiState.direccionError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.region,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Region(it)) },
                label = { Text("Región") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.comuna,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Comuna(it)) },
                label = { Text("Comuna") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.Contrasena(it)) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                isError = uiState.contrasenaError != null,
                singleLine = true
            )
            if (uiState.contrasenaError != null) {
                Text(uiState.contrasenaError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(RegistroFormEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Registrarse", fontSize = 18.sp)
            }

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(uiState.errorMessage!!, color = Color.Red, fontSize = 14.sp)
            }

            if (uiState.registroExitoso) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Registro exitoso 🌿 Redirigiendo...",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
