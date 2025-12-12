package cl.huertohogar.app.repository

import android.util.Log
import cl.huertohogar.app.model.Boleta
import cl.huertohogar.app.remote.RetrofitInstance

class BoletaRepositoryApi(
    private val tokenProvider: () -> String?
) {

    private val apiService = RetrofitInstance.create { tokenProvider() }

    suspend fun enviarBoleta(boleta: Boleta): Boleta? {
        val response = apiService.sendBoleta(boleta)

        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("API", "Error enviarBoleta: ${response.code()}")
            null
        }
    }
}
