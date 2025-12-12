package cl.huertohogar.app.model

data class WeatherResponse(
    val name: String,
    val main: MainInfo,
    val weather: List<WeatherInfo>
)

data class MainInfo(
    val temp: Double
)

data class WeatherInfo(
    val description: String
)
