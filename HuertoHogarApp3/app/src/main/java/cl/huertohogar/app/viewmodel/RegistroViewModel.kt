package cl.huertohogar.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.AppDatabase
import cl.huertohogar.app.model.User
import cl.huertohogar.app.model.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

// Estado del formulario de registro
data class RegistroUiState(
    val email: String = "",
    val password: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val isLoading: Boolean = false,
    val registroExitoso: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val nombreError: String? = null,
    val apellidoError: String? = null
)

sealed class RegistroFormEvent {
    data class EmailChanged(val value: String) : RegistroFormEvent()
    data class PasswordChanged(val value: String) : RegistroFormEvent()
    data class NombreChanged(val value: String) : RegistroFormEvent()
    data class ApellidoChanged(val value: String) : RegistroFormEvent()
    object Submit : RegistroFormEvent()
}

class RegistroViewModel(context: Context) : ViewModel() {

    // Crear repositorio internamente usando context (igual que LoginViewModel)
    private val repository: UserRepository by lazy {
        val db = AppDatabase.getDatabase(context)
        UserRepository(db.userDao())
    }

    var uiState by mutableStateOf(RegistroUiState())
        private set

    // Validador de email
    private val emailAddressPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun onEvent(event: RegistroFormEvent) {
        when (event) {
            is RegistroFormEvent.EmailChanged ->
                uiState = uiState.copy(email = event.value, emailError = null)

            is RegistroFormEvent.PasswordChanged ->
                uiState = uiState.copy(password = event.value, passwordError = null)

            is RegistroFormEvent.NombreChanged ->
                uiState = uiState.copy(nombre = event.value, nombreError = null)

            is RegistroFormEvent.ApellidoChanged ->
                uiState = uiState.copy(apellido = event.value, apellidoError = null)

            RegistroFormEvent.Submit -> submitRegistration()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true
        var newState = uiState.copy(
            emailError = null,
            passwordError = null,
            nombreError = null,
            apellidoError = null
        )

        if (newState.nombre.isBlank()) {
            newState = newState.copy(nombreError = "El nombre es obligatorio.")
            isValid = false
        }
        if (newState.apellido.isBlank()) {
            newState = newState.copy(apellidoError = "El apellido es obligatorio.")
            isValid = false
        }
        if (!emailAddressPattern.matcher(newState.email).matches()) {
            newState = newState.copy(emailError = "Ingrese un email válido.")
            isValid = false
        }
        if (newState.password.length < 6) {
            newState = newState.copy(passwordError = "La contraseña debe tener al menos 6 caracteres.")
            isValid = false
        }

        uiState = newState
        return isValid
    }

    private fun submitRegistration() {
        if (!validateFields()) {
            uiState = uiState.copy(errorMessage = "Por favor, corrija los errores del formulario.")
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                // Verificar si el email ya existe
                if (repository.isEmailRegistered(uiState.email)) {
                    uiState = uiState.copy(
                        isLoading = false,
                        emailError = "Este email ya está registrado.",
                        errorMessage = "El registro falló: el email ya existe."
                    )
                    return@launch
                }

                // Crear usuario nuevo
                val newUser = User(
                    email = uiState.email,
                    passwordHash = uiState.password,
                    nombre = uiState.nombre,
                    apellido = uiState.apellido
                )

                repository.registerUser(newUser)

                uiState = uiState.copy(
                    isLoading = false,
                    registroExitoso = true,
                    errorMessage = null
                )

                delay(1500)
                uiState = uiState.copy(registroExitoso = false)

            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Error al intentar registrar: ${e.message}"
                )
            }
        }
    }
}
