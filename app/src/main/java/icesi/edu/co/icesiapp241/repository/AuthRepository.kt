package icesi.edu.co.icesiapp241.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.AuthStatus
import icesi.edu.co.icesiapp241.services.AuthServices

interface AuthRepository {
    suspend fun login(email: String, pass: String):AppAuthState
    suspend fun signup(email: String, pass: String):AppAuthState
}

class AuthRepositoryImpl(val service:AuthServices = AuthServices()):AuthRepository{
    override suspend fun login(email:String, pass:String) : AppAuthState {
        try {
            val result = service.login(email, pass)
            result.user?.let {
                return AppAuthState.Success("")
            } ?: run {
                return AppAuthState.Error("")
            }
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error("")
        }
    }

    override suspend fun signup(email:String, pass:String) : AppAuthState {
        try {
            val result = service.signup(email, pass)
            result.user?.let {
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error(ex.errorCode)
        }
    }

}