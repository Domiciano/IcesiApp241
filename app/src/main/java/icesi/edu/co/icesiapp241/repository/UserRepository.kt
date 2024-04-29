package icesi.edu.co.icesiapp241.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.services.FileServices
import icesi.edu.co.icesiapp241.services.UserServices
import java.io.File

interface UserRepository {
    suspend fun loadUser() : User?
    fun observeUser(callback:(User)->Unit)
    suspend fun updateProfileImage(uri: Uri, filename: String)
}

class UserRepositoryImpl(
    val userServices:UserServices = UserServices(),
    val fileServices: FileServices = FileServices()
) : UserRepository{
    override suspend fun loadUser(): User? {
        val document = userServices.loadUser(Firebase.auth.uid!!)
        //Document -> Obj
        //DTO -> Domain model
        val user = document.toObject(User::class.java)
        user?.profilePic?.let {
            user.profilePic = fileServices.downloadImage(it).toString()
        }
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

    override suspend fun updateProfileImage(uri: Uri, filename: String) {
        fileServices.uploadFile(uri, filename)
        userServices.updateProfileImage(filename)
    }

    fun testrepo(){
        userServices.test({a,b,g ->
            println(a);
        })
    }





}