package icesi.edu.co.icesiapp241.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesiapp241.domain.model.Message
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.repository.AuthRepository
import icesi.edu.co.icesiapp241.repository.AuthRepositoryImpl
import icesi.edu.co.icesiapp241.repository.MessagesRepository
import icesi.edu.co.icesiapp241.repository.MessagesRepositoryImpl
import icesi.edu.co.icesiapp241.repository.UserRepository
import icesi.edu.co.icesiapp241.repository.UserRepositoryImpl
import icesi.edu.co.icesiapp241.retrofit.FCMBody
import icesi.edu.co.icesiapp241.retrofit.FCMData
import icesi.edu.co.icesiapp241.retrofit.FCMMessage
import icesi.edu.co.icesiapp241.retrofit.RetrofitConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel(
    val userRepo: UserRepository = UserRepositoryImpl(),
    val authRepo: AuthRepository = AuthRepositoryImpl(),
    val messagesRepo: MessagesRepository = MessagesRepositoryImpl()
) : ViewModel() {

    //Estado
    private val _userState = MutableLiveData<User>()
    val userState:LiveData<User> get() = _userState

    private val messages = arrayListOf<Message>()
    private val _messagesState = MutableLiveData<ArrayList<Message>>(messages)
    val messagesState:LiveData<ArrayList<Message>> get() = _messagesState


    //Los eventos de entrada
    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepo.loadUser()
            user?.let {
                withContext(Dispatchers.Main) {
                    _userState.value = it
                }
            }
        }
    }

    fun observeUser() {
        userRepo.observeUser({
            _userState.value = it
        }
        ) //alfa(fun: (String) -> Unit)
    }




    fun observeGeneralRoom(){
        messagesRepo.observe {
            messages.add(it)
            _messagesState.value = messages
        }
    }


    fun signout() {
        authRepo.signout()
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch (Dispatchers.IO){
            messagesRepo.sendMessage(message)
            var response = RetrofitConfiguration.messagingService.sendMessage(
                FCMBody(
                    FCMMessage(
                        "news",
                        FCMData(
                            "Alfa","Lorem Ipsum..."
                        )
                    )
                )
            )
            Log.e(">>>", response.name)
        }
    }

    fun updateProfileImage(uri: Uri){
        viewModelScope.launch (Dispatchers.IO){
            val id = UUID.randomUUID().toString()
            userRepo.updateProfileImage(uri, id)
            loadUser()
        }
    }


}