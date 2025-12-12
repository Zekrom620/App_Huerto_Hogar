package cl.huertohogar.app.viewmodel

import cl.huertohogar.app.model.Product
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel
    private val loginViewModelMock = mockk<LoginViewModel>(relaxed = true)

    @Before
    fun setup() {
        viewModel = CarritoViewModel(loginViewModelMock)
    }

    @Test
    fun `totalCarrito calcula correctamente el total`() {
        // Arrange: Agregamos dos productos
        val p1 = Product(1, "C1", "Manzana", 1000, "", "desc", "cat", 5, "Chile")
        val p2 = Product(2, "C2", "Plátano", 2000, "", "desc", "cat", 8, "Chile")

        viewModel.agregarProducto(p1)
        viewModel.agregarProducto(p2)

        // Act
        val total = viewModel.totalCarrito()

        // Assert
        assertEquals(3000, total)
    }
}
