package cl.huertohogar.app.model

data class Product(
    val id: Long? = null,
    val codigo: String,
    val nombre: String,
    val precio: Int,
    val imagen: String,
    val descripcion: String,
    val categoria: String,
    val stock: Int,
    val origen: String
)
