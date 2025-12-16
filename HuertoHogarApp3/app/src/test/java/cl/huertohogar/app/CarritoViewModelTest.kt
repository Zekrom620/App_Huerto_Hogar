package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel

    @Before
    fun setup() {
        viewModel = CarritoViewModel()
    }

    @Test
    fun `totalCarrito calcula correctamente con cantidades`() {

        val p1 = Product(1, "C1", "Manzana", 1000, "", "desc", "cat", 5, "Chile")
        val p2 = Product(2, "C2", "Plátano", 2000, "", "desc", "cat", 8, "Chile")

        // 3 manzanas = 3000
        viewModel.agregarProducto(p1, cantidad = 3)

        // 2 plátanos = 4000
        viewModel.agregarProducto(p2, cantidad = 2)

        val total = viewModel.totalCarrito()

        assertEquals(7000, total)
    }
}
