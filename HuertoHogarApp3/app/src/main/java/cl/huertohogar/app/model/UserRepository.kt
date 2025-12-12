package cl.huertohogar.app.model

class UserRepository(private val userDao: UserDao) {

    // Registrar un nuevo usuario
    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    // Verificar si un email ya está registrado
    suspend fun isEmailRegistered(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }

    // Login de usuario (email + password)
    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}
