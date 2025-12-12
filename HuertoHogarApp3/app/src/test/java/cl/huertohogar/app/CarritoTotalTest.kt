package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CarritoTotalTest {

    // Fake loginViewModel solo para poder instanciar CarritoViewModel
    private val loginFake = mockk<LoginViewModel>(relaxed = true)

    @Test
    fun `el total del carrito se calcula correctamente`() {
        val vm = CarritoViewModel(loginFake)

        val producto1 = Product(
            id = 1, codigo = "A1", nombre = "Manzana",
            precio = 1000, imagen = "", descripcion = "",
            categoria = "", stock = 10, origen = ""
        )

        val producto2 = Product(
            id = 2, codigo = "A2", nombre = "Naranja",
            precio = 500, imagen = "", descripcion = "",
            categoria = "", stock = 20, origen = ""
        )

        vm.agregarProducto(producto1)
        vm.agregarProducto(producto2)

        val total = vm.totalCarrito()

        assertEquals(1500, total)
    }
}
