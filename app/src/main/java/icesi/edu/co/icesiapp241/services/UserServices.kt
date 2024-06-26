package icesi.edu.co.icesiapp241.services

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import icesi.edu.co.icesiapp241.domain.model.User
import kotlinx.coroutines.tasks.await

class UserServices {

    //CRUD

    suspend fun createUser(user: User) {
        Firebase.firestore.collection("users").document(user.id).set(user).await()
    }

    suspend fun loadUser(id: String): DocumentSnapshot {
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }

    suspend fun loadUserList(): QuerySnapshot {
        val output = Firebase.firestore.collection("users").get().await()
        return output
    }

    fun observeUser(id: String, callback: (DocumentSnapshot?) -> Unit) {
        Firebase.firestore.collection("users").document(id)
            .addSnapshotListener{ snapshot, error ->
                callback(snapshot)
            }
    }

    fun test(funcion:(String,Boolean, Int)->Unit) {
       var alfa = "Luisa Castaño"
        var beta = true
        var gamma = 123
        funcion(alfa,beta,gamma)
    }

    suspend fun updateProfileImage(filename: String) {
        Firebase.firestore.collection("users").document(
            Firebase.auth.uid!!
        ).update("profilePic", filename).await()
    }


}