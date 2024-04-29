package icesi.edu.co.icesiapp241.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.services.UserServices

interface UserRepository {
    suspend fun loadUser() : User?
    fun observeUser(callback:(User)->Unit)
}

class UserRepositoryImpl(
    val userServices:UserServices = UserServices()
) : UserRepository{
    override suspend fun loadUser(): User? {
        val document = userServices.loadUser(Firebase.auth.uid!!)
        //Document -> Obj
        //DTO -> Domain model
        val user = document.toObject(User::class.java)
        return user
    }
    override fun observeUser(callback: (User) -> Unit) {
        userServices.observeUser(Firebase.auth.uid!!, { snapshot ->
            val user = snapshot?.toObject(User::class.java)

            user?.let{
                callback(it)
            }

        });
    }

    fun testrepo(){
        userServices.test({a,b,g ->
            println(a);
        })
    }





}