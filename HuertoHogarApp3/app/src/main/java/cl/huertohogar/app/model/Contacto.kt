package cl.huertohogar.app.model

data class Contacto(
    val id: Long? = null,
    val nombre: String,
    val correo: String,
    val asunto: String,
    val mensaje: String,
    val fechaEnvio: String? = null
)
