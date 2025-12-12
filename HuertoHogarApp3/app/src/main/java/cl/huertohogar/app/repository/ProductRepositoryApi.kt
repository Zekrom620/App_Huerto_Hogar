package cl.huertohogar.app.repository

import android.util.Log
import cl.huertohogar.app.model.Product
import cl.huertohogar.app.remote.RetrofitInstance

class ProductRepositoryApi(
    private val tokenProvider: () -> String?
) {

    private val apiService = RetrofitInstance.create { tokenProvider() }

    suspend fun getProducts(): List<Product> {
        val response = apiService.getProducts()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            Log.e("API", "Error getProducts")
            emptyList()
        }
    }

    suspend fun getProductById(id: Long): Product? {
        val response = apiService.getProductById(id)
        return if (response.isSuccessful) response.body() else null
    }
}
