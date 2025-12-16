package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.repository.UserRepositoryApi
import cl.huertohogar.app.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    private val repo = UserRepositoryApi { SessionManager.token }

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cambiarContrasena(passwordActual: String, passwordNueva: String) {
        val userId = SessionManager.userId

        if (userId == null) {
            _mensaje.value = "Sesión inválida. Vuelve a iniciar sesión."
            return
        }

        if (passwordActual.isBlank() || passwordNueva.isBlank()) {
            _mensaje.value = "Todos los campos son obligatorios"
            return
        }

        if (passwordNueva.length < 6) {
            _mensaje.value = "La nueva contraseña debe tener al menos 6 caracteres"
            return
        }

        viewModelScope.launch {
            val ok = repo.cambiarContrasena(
                userId = userId,
                passwordActual = passwordActual,
                passwordNueva = passwordNueva
            )

            _mensaje.value = if (ok) {
                "Contraseña actualizada correctamente"
            } else {
                "La contraseña actual es incorrecta"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
