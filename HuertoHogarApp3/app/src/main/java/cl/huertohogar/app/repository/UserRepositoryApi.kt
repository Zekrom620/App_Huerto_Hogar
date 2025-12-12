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

    private fun validateUser(user: User): Boolean {
        if (user.correo.isBlank()) return false
        if (user.contrasena.isBlank()) return false
        return true
    }

    suspend fun register(user: User): User? {
        if (!validateUser(user)) return null
        return handleResponse(apiService.registerUser(user), "REGISTER")
    }

    suspend fun login(user: User): LoginResponse? {
        if (!validateUser(user)) return null
        return handleResponse(apiService.login(user), "LOGIN")
    }

    suspend fun getUsers(): List<User> {
        return handleResponse(apiService.getUsers(), "GET_USERS") ?: emptyList()
    }
}
