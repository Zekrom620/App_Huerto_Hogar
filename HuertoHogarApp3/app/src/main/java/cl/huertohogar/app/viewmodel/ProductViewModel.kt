package cl.huertohogar.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.repository.ProductRepositoryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val loginViewModel: LoginViewModel
) : ViewModel() {

    private val repository = ProductRepositoryApi { loginViewModel.uiState.token }


    private val _productos = MutableStateFlow<List<Product>>(emptyList())
    val productos: StateFlow<List<Product>> = _productos

    private val _isLoadingLista = MutableStateFlow(false)
    val isLoadingLista: StateFlow<Boolean> = _isLoadingLista

    private val _errorLista = MutableStateFlow<String?>(null)
    val errorLista: StateFlow<String?> = _errorLista

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoadingLista.value = true
            _errorLista.value = null

            val lista = repository.getProducts()

            _productos.value = lista

            _isLoadingLista.value = false
        }
    }


    private val _productoDetalle = MutableStateFlow<Product?>(null)
    val productoDetalle: StateFlow<Product?> = _productoDetalle

    private val _isLoadingDetalle = MutableStateFlow(false)
    val isLoadingDetalle: StateFlow<Boolean> = _isLoadingDetalle

    private val _errorDetalle = MutableStateFlow<String?>(null)
    val errorDetalle: StateFlow<String?> = _errorDetalle

    fun cargarProductoPorId(id: Long) {
        viewModelScope.launch {
            _isLoadingDetalle.value = true
            _errorDetalle.value = null

            val p = repository.getProductById(id)

            if (p != null) {
                _productoDetalle.value = p
            } else {
                _errorDetalle.value = "No se encontró el producto con ID $id"
            }

            _isLoadingDetalle.value = false
        }
    }
}
