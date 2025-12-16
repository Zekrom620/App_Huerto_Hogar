package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class CarritoDetalleTest {

    @Test
    fun `subtotal se calcula correctamente segun cantidad`() {

        val vm = CarritoViewModel()

        val producto = Product(
            id = 1,
            codigo = "A1",
            nombre = "Espinaca",
            precio = 1200,
            imagen = "",
            descripcion = "",
            categoria = "",
            stock = 5,
            origen = ""
        )

        vm.agregarProducto(producto, cantidad = 3)

        val item = vm.carrito.value.first()

        // 1200 x 3 = 3600
        assertEquals(3600, item.subtotal())
    }
}
