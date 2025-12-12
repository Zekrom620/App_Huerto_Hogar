package cl.huertohogar.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.LoginResponse
import cl.huertohogar.app.model.User
import cl.huertohogar.app.repository.UserRepositoryApi
import kotlinx.coroutines.launch

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false,
    val errorMessage: String? = null,
    val correoError: String? = null,
    val contrasenaError: String? = null,

    // Nueva información almacenada tras login
    val token: String? = null,
    val userId: Long? = null,
    val rol: String? = null
)

sealed class LoginFormEvent {
    data class CorreoChanged(val value: String) : LoginFormEvent()
    data class ContrasenaChanged(val value: String) : LoginFormEvent()
    object Submit : LoginFormEvent()
}

class LoginViewModel : ViewModel() {

    private val repository = UserRepositoryApi()

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.CorreoChanged ->
                uiState = uiState.copy(correo = event.value, correoError = null)

            is LoginFormEvent.ContrasenaChanged ->
                uiState = uiState.copy(contrasena = event.value, contrasenaError = null)

            LoginFormEvent.Submit -> submitLogin()
        }
    }

    private fun validar(): Boolean {
        var valido = true
        var state = uiState.copy(correoError = null, contrasenaError = null)

        if (state.correo.isBlank()) {
            state = state.copy(correoError = "El correo no puede estar vacío.")
            valido = false
        }
        if (state.contrasena.isBlank()) {
            state = state.copy(contrasenaError = "La contraseña no puede estar vacía.")
            valido = false
        }

        uiState = state
        return valido
    }

    private fun submitLogin() {
        if (!validar()) return

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val loginUser = User(
                id = null,
                nombre = "",
                correo = uiState.correo,
                contrasena = uiState.contrasena,
                rut = "",
                direccion = "",
                region = "",
                comuna = ""
            )

            val response: LoginResponse? = repository.login(loginUser)

            if (response != null) {
                // Guardamos token, userId y rol en el estado
                uiState = uiState.copy(
                    isLoading = false,
                    loginExitoso = true,
                    token = response.token,
                    userId = response.userId,
                    rol = response.rol
                )
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Credenciales incorrectas."
                )
            }
        }
    }
}
