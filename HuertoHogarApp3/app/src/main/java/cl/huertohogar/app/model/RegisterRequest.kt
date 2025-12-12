package cl.huertohogar.app.model

data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rut: String,
    val direccion: String?,
    val region: String,
    val comuna: String
)
