package cl.huertohogar.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.huertohogar.app.R
import cl.huertohogar.app.navigation.Destinos
import cl.huertohogar.app.utils.SessionManager
import cl.huertohogar.app.viewmodel.ChangePasswordViewModel
import cl.huertohogar.app.viewmodel.LoginViewModel
import cl.huertohogar.app.viewmodel.PerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    perfilVM: PerfilViewModel,
    passwordVM: ChangePasswordViewModel
) {

    val user = SessionManager.user

    var direccion by remember { mutableStateOf(user?.direccion ?: "") }
    var region by remember { mutableStateOf(user?.region ?: "") }
    var comuna by remember { mutableStateOf(user?.comuna ?: "") }

    var editar by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    var passActual by remember { mutableStateOf("") }
    var passNueva by remember { mutableStateOf("") }

    val mensajePerfil by perfilVM.mensaje.collectAsState()
    val mensajePassword by passwordVM.mensaje.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Destinos.Lobby.ruta) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.ic_plantita),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(user?.nombre ?: "", style = MaterialTheme.typography.headlineSmall)
            Text(user?.correo ?: "")

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                enabled = editar,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = region,
                onValueChange = { region = it },
                label = { Text("Región") },
                enabled = editar,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = comuna,
                onValueChange = { comuna = it },
                label = { Text("Comuna") },
                enabled = editar,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (editar) {
                        perfilVM.guardarPerfil(direccion, region, comuna)
                    }
                    editar = !editar
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editar) "Guardar cambios" else "Editar perfil")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { showPassword = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Lock, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Cambiar contraseña")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { showDelete = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Eliminar cuenta")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    SessionManager.clear()
                    loginViewModel.reset()
                    navController.navigate(Destinos.Bienvenida.ruta) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión")
            }

            mensajePerfil?.let {
                Spacer(Modifier.height(12.dp))
                Text(it)
            }
        }
    }

    /* =========================
       🔐 CAMBIAR CONTRASEÑA
       ========================= */
    if (showPassword) {
        AlertDialog(
            onDismissRequest = { showPassword = false },
            title = { Text("Cambiar contraseña") },
            text = {
                Column {
                    OutlinedTextField(
                        value = passActual,
                        onValueChange = { passActual = it },
                        label = { Text("Contraseña actual") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = passNueva,
                        onValueChange = { passNueva = it },
                        label = { Text("Nueva contraseña") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    mensajePassword?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(it)

                        if (it.contains("correctamente")) {
                            LaunchedEffect(Unit) {
                                passActual = ""
                                passNueva = ""
                                showPassword = false
                                passwordVM.limpiarMensaje()
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        passwordVM.cambiarContrasena(passActual, passNueva)
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPassword = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    /* =========================
       🗑️ ELIMINAR CUENTA
       ========================= */
    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Eliminar cuenta") },
            text = {
                Text("Esta acción es irreversible. ¿Deseas continuar?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        perfilVM.eliminarCuenta {
                            SessionManager.clear()
                            loginViewModel.reset()
                            navController.navigate(Destinos.Bienvenida.ruta) {
                                popUpTo(0)
                            }
                        }
                    }
                ) {
                    Text(
                        "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDelete = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
