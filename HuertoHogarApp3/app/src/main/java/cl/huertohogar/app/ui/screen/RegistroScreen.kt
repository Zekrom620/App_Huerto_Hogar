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
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.viewmodel.RegistroFormEvent
import cl.huertohogar.app.viewmodel.RegistroViewModel
import kotlinx.coroutines.delay
import cl.huertohogar.app.model.UserRepository
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme

@Composable
fun RegistroScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    val uiState = viewModel.uiState

    var scale by remember { mutableFloatStateOf(0.9f) }
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
                    listOf(
                        Color(0xFF4CAF50),
                        Color(0xFFA5D6A7)
                    )
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
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true,
                isError = uiState.nombreError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (uiState.nombreError != null) {
                Text(text = uiState.nombreError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            } else {
                Text(text = "Ingrese su nombre completo.", fontSize = 12.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.apellido,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.ApellidoChanged(it)) },
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true,
                isError = uiState.apellidoError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (uiState.apellidoError != null) {
                Text(text = uiState.apellidoError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            } else {
                Text(text = "Ingrese su apellido completo.", fontSize = 12.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.EmailChanged(it)) },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true,
                isError = uiState.emailError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (uiState.emailError != null) {
                Text(text = uiState.emailError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            } else {
                Text(text = "Ingrese un email válido.", fontSize = 12.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(RegistroFormEvent.PasswordChanged(it)) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                singleLine = true,
                isError = uiState.passwordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (uiState.passwordError != null) {
                Text(text = uiState.passwordError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            } else {
                Text(text = "La contraseña debe tener al menos 6 caracteres.", fontSize = 12.sp, color = Color.White)
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
                Text(text = uiState.errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }

            if (uiState.registroExitoso) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Registro exitoso 🌿 Redirigiendo al login...",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


