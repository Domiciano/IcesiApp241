package icesi.edu.co.icesiapp241.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.AuthStatus
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.repository.AuthRepository
import icesi.edu.co.icesiapp241.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val repo:AuthRepository = AuthRepositoryImpl()) : ViewModel() {

    val authStatus = MutableLiveData<AppAuthState>()

    fun login(email: String, pass:String) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){
                authStatus.value = AppAuthState.Loading("Cargando...")
            }
            val status = repo.login(email, pass) //10s
            withContext(Dispatchers.Main){authStatus.value = status}

        }
    }

}