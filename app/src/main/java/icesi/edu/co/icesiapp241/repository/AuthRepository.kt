package icesi.edu.co.icesiapp241.repository

import com.google.firebase.auth.FirebaseAuthException
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.services.AuthServices
import icesi.edu.co.icesiapp241.services.UserServices

interface AuthRepository {
    suspend fun login(email: String, pass: String): AppAuthState
    suspend fun signup(user: User, pass: String): AppAuthState
}

class AuthRepositoryImpl(
    val authServices: AuthServices = AuthServices(),
    val userServices: UserServices = UserServices()
) : AuthRepository {
    override suspend fun login(email: String, pass: String): AppAuthState {
        try {
            val result = authServices.login(email, pass)
            result.user?.let {
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        } catch (ex: FirebaseAuthException) {
            return AppAuthState.Error(ex.errorCode)
        }
    }

    override suspend fun signup(user: User, pass: String): AppAuthState {
        try {
            //1. Registro
            val result = authServices.signup(user.email, pass)
            result.user?.let {
                //2. Guardar usuario en base de datos
                user.id = it.uid
                userServices.createUser(user)
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        } catch (ex: FirebaseAuthException) {
            return AppAuthState.Error(ex.errorCode)
        }
    }

}