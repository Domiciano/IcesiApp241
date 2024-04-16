package icesi.edu.co.icesiapp241

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import icesi.edu.co.icesiapp241.databinding.ActivityProfileBinding
import icesi.edu.co.icesiapp241.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel:ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.observeUser()

        viewmodel.userState.observe(this){
            binding.emailTV.text = it.email
            binding.nameTV.text = it.name
        }

    }


}