package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.User
import cl.huertohogar.app.repository.UserRepositoryApi
import cl.huertohogar.app.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    private val repo = UserRepositoryApi { SessionManager.token }

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun guardarPerfil(
        direccion: String,
        region: String,
        comuna: String
    ) {
        val user = SessionManager.user ?: return

        val actualizado = user.copy(
            direccion = direccion,
            region = region,
            comuna = comuna
        )

        viewModelScope.launch {
            val response = repo.actualizarPerfil(actualizado)
            if (response != null) {
                SessionManager.user = response
                _mensaje.value = "Perfil actualizado correctamente"
            } else {
                _mensaje.value = "Error al actualizar perfil"
            }
        }
    }

    fun eliminarCuenta(onSuccess: () -> Unit) {
        val userId = SessionManager.userId ?: return

        viewModelScope.launch {
            val ok = repo.eliminarCuenta(userId)
            if (ok) {
                SessionManager.clear()
                onSuccess()
            } else {
                _mensaje.value = "No se pudo eliminar la cuenta"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
