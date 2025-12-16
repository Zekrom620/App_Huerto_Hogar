package cl.huertohogar.app.utils

import cl.huertohogar.app.model.User

object SessionManager {
    var token: String? = null
    var userId: Long? = null
    var rol: String? = null
    var user: User? = null

    fun clear() {
        token = null
        userId = null
        rol = null
        user = null
    }

    fun isLoggedIn(): Boolean = token != null && userId != null
}
