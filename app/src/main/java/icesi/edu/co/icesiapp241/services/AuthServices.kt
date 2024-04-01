package icesi.edu.co.icesiapp241.services

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthServices {

    suspend fun signup(email:String, pass:String) : AuthResult {
        return Firebase.auth.createUserWithEmailAndPassword(email, pass).await()
    }

    suspend fun login(email:String, pass:String) : AuthResult{
        return Firebase.auth.signInWithEmailAndPassword(email, pass).await()
    }


}