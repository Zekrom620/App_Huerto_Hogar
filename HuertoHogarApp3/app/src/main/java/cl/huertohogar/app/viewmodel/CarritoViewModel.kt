package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.Boleta
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.repository.BoletaRepositoryApi
import cl.huertohogar.app.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CarritoItem(
    val producto: Product,
    var cantidad: Int
) {
    fun subtotal(): Int = producto.precio * cantidad
}

class CarritoViewModel : ViewModel() {

    private val repo = BoletaRepositoryApi { SessionManager.token }

    private val _carrito = MutableStateFlow<List<CarritoItem>>(emptyList())
    val carrito: StateFlow<List<CarritoItem>> = _carrito

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> = _errorMensaje

    private val _compraExitosa = MutableStateFlow(false)
    val compraExitosa: StateFlow<Boolean> = _compraExitosa

    /* =========================
       🛒 AGREGAR PRODUCTO
       ========================= */
    fun agregarProducto(producto: Product, cantidad: Int) {

        val lista = _carrito.value.toMutableList()
        val index = lista.indexOfFirst { it.producto.id == producto.id }

        if (index >= 0) {
            val item = lista[index]
            val nuevaCantidad = item.cantidad + cantidad

            if (nuevaCantidad <= producto.stock) {
                lista[index] = item.copy(cantidad = nuevaCantidad)
            }
        } else {
            lista.add(CarritoItem(producto, cantidad))
        }

        _carrito.value = lista
    }

    /* =========================
       ❌ ELIMINAR PRODUCTO
       ========================= */
    fun eliminarProducto(productoId: Long) {
        _carrito.value = _carrito.value.filterNot {
            it.producto.id == productoId
        }
    }

    /* =========================
       🧮 TOTAL
       ========================= */
    fun totalCarrito(): Int {
        return _carrito.value.sumOf { it.subtotal() }
    }

    /* =========================
       🧾 ENVIAR BOLETA
       ========================= */
    fun enviarBoleta(userId: Long) {

        if (_carrito.value.isEmpty()) {
            _errorMensaje.value = "El carrito está vacío"
            return
        }

        val detalle = _carrito.value.joinToString("\n") {
            "- ${it.producto.nombre} x${it.cantidad}: $${it.subtotal()}"
        }

        val boleta = Boleta(
            id = null,
            userId = userId,
            total = totalCarrito(),
            detalleProductos = detalle,
            estado = "pendiente"
        )

        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null

            val response = repo.enviarBoleta(boleta)

            _isLoading.value = false

            if (response != null) {
                _compraExitosa.value = true
            } else {
                _errorMensaje.value = "Error al procesar la compra"
            }
        }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }

    fun resetearCompraExitosa() {
        _compraExitosa.value = false
    }
}
