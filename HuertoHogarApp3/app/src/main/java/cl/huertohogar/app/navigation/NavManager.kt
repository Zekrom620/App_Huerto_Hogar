package cl.huertohogar.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.huertohogar.app.ui.screen.*
import cl.huertohogar.app.viewmodel.LoginViewModel
import cl.huertohogar.app.viewmodel.RegistroViewModel

sealed class Destinos(val ruta: String) {
    object Bienvenida : Destinos("bienvenida")
    object Login : Destinos("login")
    object Registro : Destinos("registro")
    object Catalogo : Destinos("catalogo")
    object DetalleProducto : Destinos("detalleProducto/{codigo}")
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
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Destinos.Bienvenida.ruta) {

        composable(Destinos.Bienvenida.ruta) {
            BienvenidaScreen(navController)
        }

        composable(Destinos.Login.ruta) {
            val loginViewModel = remember { LoginViewModel(context) }
            LoginScreen(navController, loginViewModel)
        }

        composable(Destinos.Registro.ruta) {
            val registroViewModel = remember { RegistroViewModel(context) }
            RegistroScreen(navController, registroViewModel)
        }

        composable(Destinos.Catalogo.ruta) {
            CatalogoScreen(navController)
        }

        composable(
            route = Destinos.DetalleProducto.ruta,
            arguments = listOf(navArgument("codigo") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoProducto = backStackEntry.arguments?.getString("codigo") ?: ""
            DetalleProductoScreen(navController, codigo = codigoProducto)
        }

        composable(Destinos.Carrito.ruta) {
            CarritoScreen(navController)
        }

        composable(Destinos.Perfil.ruta) {
            PerfilScreen(navController)
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

        composable(Destinos.Lobby.ruta) {
            LobbyScreen(navController)
        }
    }
}
