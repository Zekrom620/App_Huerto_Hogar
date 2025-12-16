package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class CarritoTotalTest {

    @Test
    fun `el total del carrito se calcula correctamente con multiples productos`() {

        val vm = CarritoViewModel()

        val producto1 = Product(
            id = 1,
            codigo = "A1",
            nombre = "Manzana",
            precio = 1000,
            imagen = "",
            descripcion = "",
            categoria = "",
            stock = 10,
            origen = ""
        )

        val producto2 = Product(
            id = 2,
            codigo = "A2",
            nombre = "Naranja",
            precio = 500,
            imagen = "",
            descripcion = "",
            categoria = "",
            stock = 20,
            origen = ""
        )

        // 2 manzanas = 2000
        vm.agregarProducto(producto1, cantidad = 2)

        // 3 naranjas = 1500
        vm.agregarProducto(producto2, cantidad = 3)

        val total = vm.totalCarrito()

        assertEquals(3500, total)
    }
}
