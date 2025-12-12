package cl.huertohogar.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.AppDatabase
import cl.huertohogar.app.model.UserRepository
import kotlinx.coroutines.launch

// Estado del formulario de login
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

// Eventos que puede emitir el formulario de login
sealed class LoginFormEvent {
    data class EmailChanged(val value: String) : LoginFormEvent()
    data class PasswordChanged(val value: String) : LoginFormEvent()
    object Submit : LoginFormEvent()
}

class LoginViewModel(context: Context) : ViewModel() {

    // Crear repositorio internamente usando context
    private val repository: UserRepository by lazy {
        val db = AppDatabase.getDatabase(context)
        UserRepository(db.userDao())
    }

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> uiState = uiState.copy(email = event.value, emailError = null)
            is LoginFormEvent.PasswordChanged -> uiState = uiState.copy(password = event.value, passwordError = null)
            LoginFormEvent.Submit -> submitLogin()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true
        var newState = uiState.copy(emailError = null, passwordError = null)

        if (newState.email.isBlank()) {
            newState = newState.copy(emailError = "El email no puede estar vacío.")
            isValid = false
        }
        if (newState.password.isBlank()) {
            newState = newState.copy(passwordError = "La contraseña no puede estar vacía.")
            isValid = false
        }

        uiState = newState
        return isValid
    }

    private fun submitLogin() {
        if (!validateFields()) {
            uiState = uiState.copy(errorMessage = "Por favor, ingrese sus credenciales.")
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val user = repository.loginUser(uiState.email, uiState.password)

                if (user != null) {
                    uiState = uiState.copy(
                        isLoading = false,
                        loginExitoso = true,
                        errorMessage = null
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Credenciales incorrectas. Verifique su email y contraseña."
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Error de conexión/base de datos: ${e.message}"
                )
            }
        }
    }
}
