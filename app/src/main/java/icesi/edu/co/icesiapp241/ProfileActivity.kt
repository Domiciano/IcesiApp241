package icesi.edu.co.icesiapp241

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import icesi.edu.co.icesiapp241.adapters.ChatAdapter
import icesi.edu.co.icesiapp241.databinding.ActivityProfileBinding
import icesi.edu.co.icesiapp241.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel:ProfileViewModel by viewModels()

    val adapter = ChatAdapter()

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

            binding.messagesRV.setHasFixedSize(true)
            binding.messagesRV.layoutManager = LinearLayoutManager(this)
            binding.messagesRV.adapter = adapter



        } ?: run{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }



    }


}