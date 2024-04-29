package icesi.edu.co.icesiapp241

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import icesi.edu.co.icesiapp241.adapters.ChatAdapter
import icesi.edu.co.icesiapp241.databinding.ActivityProfileBinding
import icesi.edu.co.icesiapp241.domain.model.Message
import icesi.edu.co.icesiapp241.viewmodel.ProfileViewModel


class ProfileActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel: ProfileViewModel by viewModels()

    val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.messagesRV.setHasFixedSize(true)
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messagesRV.layoutManager = manager
        binding.messagesRV.adapter = adapter

        Firebase.auth.currentUser?.let {
            viewmodel.observeUser()
            viewmodel.observeGeneralRoom()

            binding.signoutButton.setOnClickListener {
                viewmodel.signout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            viewmodel.messagesState.observe(this){
                adapter.messages = it
                adapter.notifyDataSetChanged()
                if(adapter.itemCount>0) {
                    binding.messagesRV.smoothScrollToPosition(it.lastIndex);
                }
            }

            binding.sendButton.setOnClickListener {
                viewmodel.sendMessage(
                    Message(
                        Firebase.auth.uid!!,
                        binding.messageET.text.toString(),
                        Timestamp.now()
                    )
                )
            }


            viewmodel.userState.observe(this) {
                binding.emailTV.text = it.email
                binding.nameTV.text = it.name
            }



        } ?: run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }



}