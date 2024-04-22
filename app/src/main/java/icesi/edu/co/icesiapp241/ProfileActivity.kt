package icesi.edu.co.icesiapp241

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

        Firebase.auth.currentUser?.let {
            viewmodel.observeUser()

            binding.signoutButton.setOnClickListener {
                viewmodel.signout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }


            viewmodel.userState.observe(this){
                binding.emailTV.text = it.email
                binding.nameTV.text = it.name
            }
        } ?: run{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }


}