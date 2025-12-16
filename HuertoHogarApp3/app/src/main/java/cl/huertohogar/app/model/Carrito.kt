package cl.huertohogar.app.model

data class CarritoItem(
    val producto: Product,
    var cantidad: Int
) {
    fun subtotal(): Int {
        return producto.precio * cantidad
    }
}