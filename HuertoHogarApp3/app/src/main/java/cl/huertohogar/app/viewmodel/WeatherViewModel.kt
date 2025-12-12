package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.WeatherResponse
import cl.huertohogar.app.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repo = WeatherRepository()

    private val apiKey = "94402a537d5c394b71370860d38839e5" // tu KEY

    private val _clima = MutableStateFlow<WeatherResponse?>(null)
    val clima: StateFlow<WeatherResponse?> = _clima

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarClima(ciudad: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val data = repo.obtenerClima(ciudad, apiKey)

                if (data != null) {
                    _clima.value = data
                } else {
                    _error.value = "Error al obtener clima"
                }

            } catch (e: Exception) {
                _error.value = "No hay conexión o API no activa"
            }

            _isLoading.value = false
        }
    }
}
