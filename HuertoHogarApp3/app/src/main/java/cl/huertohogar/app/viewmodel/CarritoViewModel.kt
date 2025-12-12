package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.Boleta
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.repository.BoletaRepositoryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val loginViewModel: LoginViewModel
) : ViewModel() {

    private val repo = BoletaRepositoryApi { loginViewModel.uiState.token }

    private val _carrito = MutableStateFlow<List<Product>>(emptyList())
    val carrito: StateFlow<List<Product>> = _carrito

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> = _errorMensaje

    private val _compraExitosa = MutableStateFlow(false)
    val compraExitosa: StateFlow<Boolean> = _compraExitosa

    fun agregarProducto(p: Product) {
        _carrito.value = _carrito.value + p
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }

    fun totalCarrito(): Int = _carrito.value.sumOf { it.precio }

    fun generarDetalleTexto(): String =
        _carrito.value.joinToString("\n") { "- ${it.nombre}: $${it.precio}" }

    fun enviarBoleta(userId: Long) {

        if (_carrito.value.isEmpty()) {
            _errorMensaje.value = "El carrito está vacío."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null

            val boleta = Boleta(
                id = null,
                userId = userId,
                total = totalCarrito(),
                detalleProductos = generarDetalleTexto(),
                estado = "Pendiente",
                fechaCompra = null
            )

            val response = repo.enviarBoleta(boleta)

            if (response != null) {
                _compraExitosa.value = true
            } else {
                _errorMensaje.value = "No se pudo completar la compra."
            }

            _isLoading.value = false
        }
    }

    fun resetearCompraExitosa() {
        _compraExitosa.value = false
    }
}
