package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test

class CarritoDetalleTest {

    private val loginFake = mockk<LoginViewModel>(relaxed = true)

    @Test
    fun `detalle de productos se genera con el formato correcto`() {
        val vm = CarritoViewModel(loginFake)

        val producto = Product(
            id = 1, codigo = "A1", nombre = "Espinaca",
            precio = 1200, imagen = "", descripcion = "",
            categoria = "", stock = 5, origen = ""
        )

        vm.agregarProducto(producto)

        val detalle = vm.generarDetalleTexto()

        // Debe contener "- Espinaca: $1200"
        assertTrue(detalle.contains("- Espinaca: \$1200"))
    }
}
