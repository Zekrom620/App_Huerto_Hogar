package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.Boleta
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.repository.BoletaRepositoryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BoletaViewModel(
    private val tokenProvider: () -> String?   // ← AGREGADO
) : ViewModel() {

    private val repository = BoletaRepositoryApi(tokenProvider)

    private val _carrito = MutableStateFlow<List<Product>>(emptyList())
    val carrito: StateFlow<List<Product>> = _carrito

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _checkoutExitoso = MutableStateFlow(false)
    val checkoutExitoso: StateFlow<Boolean> = _checkoutExitoso

    fun agregarAlCarrito(producto: Product) {
        _carrito.value = _carrito.value + producto
    }

    fun eliminarDelCarrito(producto: Product) {
        _carrito.value = _carrito.value.filterNot { it.id == producto.id }
    }

    fun calcularTotal(): Int = _carrito.value.sumOf { it.precio }

    fun enviarBoleta(userId: Long) {
        val productos = _carrito.value
        if (productos.isEmpty()) {
            _error.value = "El carrito está vacío."
            return
        }

        val detalle = productos.joinToString("; ") { "${it.nombre} = ${it.precio}" }

        val boleta = Boleta(
            userId = userId,
            total = calcularTotal(),
            detalleProductos = detalle
        )

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val response = repository.enviarBoleta(boleta)

            if (response != null) {
                _checkoutExitoso.value = true
                _carrito.value = emptyList()
            } else {
                _error.value = "Error al enviar boleta."
            }

            _isLoading.value = false
        }
    }
}
