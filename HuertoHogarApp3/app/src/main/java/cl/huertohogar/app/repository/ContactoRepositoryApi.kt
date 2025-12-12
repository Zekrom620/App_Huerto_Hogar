package cl.huertohogar.app.repository

import android.util.Log
import cl.huertohogar.app.model.Contacto
import cl.huertohogar.app.remote.RetrofitInstance

class ContactoRepositoryApi(
    private val tokenProvider: () -> String?
) {

    private val apiService = RetrofitInstance.create { tokenProvider() }

    suspend fun enviarFormulario(contacto: Contacto): Contacto? {
        val response = apiService.sendContactForm(contacto)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("API", "Error enviarFormulario: ${response.code()}")
            null
        }
    }
}
