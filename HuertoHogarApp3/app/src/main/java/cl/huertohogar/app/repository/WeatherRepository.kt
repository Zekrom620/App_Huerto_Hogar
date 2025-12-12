package cl.huertohogar.app.repository

import cl.huertohogar.app.remote.WeatherRetrofit
import cl.huertohogar.app.model.WeatherResponse

class WeatherRepository {

    suspend fun obtenerClima(city: String, apiKey: String): WeatherResponse? {
        return try {
            val response = WeatherRetrofit.api.getWeather(city, apiKey)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}
