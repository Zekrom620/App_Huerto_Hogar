package cl.huertohogar.app.repository

import android.util.Log
import cl.huertohogar.app.model.LoginResponse
import cl.huertohogar.app.model.User
import cl.huertohogar.app.remote.RetrofitInstance
import retrofit2.Response

class UserRepositoryApi(
    private val tokenProvider: () -> String? = { null }
) {

    private val apiService = RetrofitInstance.create { tokenProvider() }

    private fun <T> handleResponse(response: Response<T>, tag: String): T? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("API-$tag", "Error ${response.code()}: ${response.errorBody()?.string()}")
            null
        }
    }

    suspend fun register(user: User): User? =
        handleResponse(apiService.registerUser(user), "REGISTER")

    suspend fun login(user: User): LoginResponse? =
        handleResponse(apiService.login(user), "LOGIN")

    suspend fun getUsers(): List<User> =
        handleResponse(apiService.getUsers(), "GET_USERS") ?: emptyList()

    // 🔐 Cambio de contraseña (ya listo)
    suspend fun cambiarContrasena(
        userId: Long,
        passwordActual: String,
        passwordNueva: String
    ): Boolean {
        val body = mapOf(
            "passwordActual" to passwordActual,
            "passwordNueva" to passwordNueva
        )
        return apiService.changePassword(userId, body).isSuccessful
    }

    // 🟢 NUEVO: actualizar perfil
    suspend fun actualizarPerfil(user: User): User? {
        val id = user.id ?: return null
        return handleResponse(apiService.updateUser(id, user), "UPDATE_PROFILE")
    }

    // 🟢 NUEVO: eliminar cuenta
    suspend fun eliminarCuenta(userId: Long): Boolean {
        return apiService.deleteUser(userId).isSuccessful
    }
}
