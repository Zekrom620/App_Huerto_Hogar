package cl.huertohogar.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.User
import cl.huertohogar.app.repository.UserRepositoryApi
import kotlinx.coroutines.launch

data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val rut: String = "",
    val direccion: String = "",
    val region: String = "",
    val comuna: String = "",
    val isLoading: Boolean = false,
    val registroExitoso: Boolean = false,
    val errorMessage: String? = null,

    val nombreError: String? = null,
    val correoError: String? = null,
    val contrasenaError: String? = null,
    val rutError: String? = null,
    val direccionError: String? = null
)

sealed class RegistroFormEvent {
    data class Nombre(val value: String) : RegistroFormEvent()
    data class Correo(val value: String) : RegistroFormEvent()
    data class Contrasena(val value: String) : RegistroFormEvent()
    data class Rut(val value: String) : RegistroFormEvent()
    data class Direccion(val value: String) : RegistroFormEvent()
    data class Region(val value: String) : RegistroFormEvent()
    data class Comuna(val value: String) : RegistroFormEvent()
    object Submit : RegistroFormEvent()
}

class RegistroViewModel : ViewModel() {

    private val repository = UserRepositoryApi()

    var uiState by mutableStateOf(RegistroUiState())
        private set

    fun onEvent(event: RegistroFormEvent) {
        when (event) {
            is RegistroFormEvent.Nombre -> uiState = uiState.copy(nombre = event.value, nombreError = null)
            is RegistroFormEvent.Correo -> uiState = uiState.copy(correo = event.value, correoError = null)
            is RegistroFormEvent.Contrasena -> uiState = uiState.copy(contrasena = event.value, contrasenaError = null)
            is RegistroFormEvent.Rut -> uiState = uiState.copy(rut = event.value, rutError = null)
            is RegistroFormEvent.Direccion -> uiState = uiState.copy(direccion = event.value, direccionError = null)
            is RegistroFormEvent.Region -> uiState = uiState.copy(region = event.value)
            is RegistroFormEvent.Comuna -> uiState = uiState.copy(comuna = event.value)
            RegistroFormEvent.Submit -> registrar()
        }
    }

    private fun validar(): Boolean {
        var ok = true
        var st = uiState

        if (st.nombre.isBlank()) { st = st.copy(nombreError = "Requerido"); ok = false }
        if (st.correo.isBlank()) { st = st.copy(correoError = "Requerido"); ok = false }
        if (st.contrasena.length < 6) { st = st.copy(contrasenaError = "Mínimo 6 caracteres"); ok = false }
        if (st.rut.isBlank()) { st = st.copy(rutError = "Requerido"); ok = false }
        if (st.direccion.isBlank()) { st = st.copy(direccionError = "Requerido"); ok = false }

        uiState = st
        return ok
    }

    private fun registrar() {
        if (!validar()) return

        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {

            val user = User(
                id = null,
                nombre = uiState.nombre,
                correo = uiState.correo,
                contrasena = uiState.contrasena,
                rut = uiState.rut,
                direccion = uiState.direccion,
                region = uiState.region,
                comuna = uiState.comuna
            )

            val response = repository.register(user)

            if (response != null) {
                uiState = uiState.copy(registroExitoso = true, isLoading = false)
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Error al registrar usuario."
                )
            }
        }
    }
}
