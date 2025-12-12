package cl.huertohogar.app.model

data class LoginResponse(
    val token: String,
    val userId: Long,
    val rol: String
)
