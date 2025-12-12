package cl.huertohogar.app.model

data class Boleta(
    val id: Long? = null,
    val userId: Long,
    val total: Int,
    val detalleProductos: String,
    val estado: String? = "Pendiente",
    val fechaCompra: String? = null
)
