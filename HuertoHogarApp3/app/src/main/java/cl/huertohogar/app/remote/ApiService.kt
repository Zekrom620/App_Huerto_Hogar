package cl.huertohogar.app.remote

import cl.huertohogar.app.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // =======================
    // USERS
    // =======================

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @POST("users/register")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("users/login")
    suspend fun login(@Body credentials: User): Response<LoginResponse>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: User
    ): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<Unit>

    // 🔐 NUEVO: cambiar contraseña
    @PUT("users/{id}/password")
    suspend fun changePassword(
        @Path("id") id: Long,
        @Body body: Map<String, String>
    ): Response<Map<String, String>>

    // =======================
    // PRODUCTS
    // =======================

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>

    @POST("products")
    suspend fun createProduct(@Body product: Product): Response<Product>

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body product: Product
    ): Response<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>

    // =======================
    // BOLETA
    // =======================

    @POST("checkout")
    suspend fun sendBoleta(@Body boleta: Boleta): Response<Boleta>

    // =======================
    // CONTACTO
    // =======================

    @POST("contact")
    suspend fun sendContactForm(@Body contacto: Contacto): Response<Contacto>
}
