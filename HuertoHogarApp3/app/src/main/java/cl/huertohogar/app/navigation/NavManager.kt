package cl.huertohogar.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.huertohogar.app.ui.screen.*
import cl.huertohogar.app.viewmodel.*

sealed class Destinos(val ruta: String) {
    object Bienvenida : Destinos("bienvenida")
    object Login : Destinos("login")
    object Registro : Destinos("registro")
    object Catalogo : Destinos("catalogo")
    object DetalleProducto : Destinos("detalleProducto/{id}")
    object Carrito : Destinos("carrito")
    object Perfil : Destinos("perfil")
    object Seguimiento : Destinos("seguimiento")
    object Nosotros : Destinos("nosotros")
    object Confirmacion : Destinos("confirmacion")
    object Lobby : Destinos("lobby")
}

@Composable
fun NavManager() {

    val navController = rememberNavController()

    val loginViewModel = remember { LoginViewModel() }
    val registroViewModel = remember { RegistroViewModel() }
    val productViewModel = remember { ProductViewModel() }
    val carritoViewModel = remember { CarritoViewModel() }

    val perfilViewModel = remember { PerfilViewModel() }
    val changePasswordViewModel = remember { ChangePasswordViewModel() }

    NavHost(
        navController = navController,
        startDestination = Destinos.Bienvenida.ruta
    ) {

        composable(Destinos.Bienvenida.ruta) {
            BienvenidaScreen(navController)
        }

        composable(Destinos.Login.ruta) {
            LaunchedEffect(loginViewModel.uiState.loginExitoso) {
                if (loginViewModel.uiState.loginExitoso) {
                    navController.navigate(Destinos.Lobby.ruta) {
                        popUpTo(Destinos.Login.ruta) { inclusive = true }
                    }
                }
            }
            LoginScreen(navController, loginViewModel)
        }

        composable(Destinos.Registro.ruta) {
            RegistroScreen(navController, registroViewModel)
        }

        composable(Destinos.Lobby.ruta) {
            LobbyScreen(navController)
        }

        composable(Destinos.Catalogo.ruta) {
            CatalogoScreen(
                navController = navController,
                viewModel = productViewModel
            )
        }

        composable(
            route = Destinos.DetalleProducto.ruta,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            DetalleProductoScreen(
                navController = navController,
                viewModel = productViewModel,
                carritoViewModel = carritoViewModel,
                idProducto = id
            )
        }

        composable(Destinos.Carrito.ruta) {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel
            )
        }

        composable(Destinos.Perfil.ruta) {
            PerfilScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                perfilVM = perfilViewModel,
                passwordVM = changePasswordViewModel
            )
        }

        composable(Destinos.Seguimiento.ruta) {
            SeguimientoScreen(navController)
        }

        composable(Destinos.Nosotros.ruta) {
            NosotrosScreen(navController)
        }

        composable(Destinos.Confirmacion.ruta) {
            ConfirmacionScreen(navController)
        }
    }
}
