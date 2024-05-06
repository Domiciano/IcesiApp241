package icesi.edu.co.icesiapp241

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.messaging.messaging
import com.google.firebase.storage.storage
import icesi.edu.co.icesiapp241.adapters.ChatAdapter
import icesi.edu.co.icesiapp241.databinding.ActivityProfileBinding
import icesi.edu.co.icesiapp241.domain.model.Message
import icesi.edu.co.icesiapp241.viewmodel.ProfileViewModel
import java.util.UUID


class ProfileActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel: ProfileViewModel by viewModels()

    val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissions(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS
        ), 1)


        Firebase.messaging.subscribeToTopic("news").addOnSuccessListener {
            Log.e(">>>", "Sucrito")
        }


        val galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK) {
                var uri = it.data?.data
                Log.e(">>>", uri.toString())

                uri?.let {
                    viewmodel.updateProfileImage(uri)
                }


            }else if(it.resultCode == RESULT_CANCELED){

            }

        }

        binding.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }


        binding.messagesRV.setHasFixedSize(true)
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messagesRV.layoutManager = manager
        binding.messagesRV.adapter = adapter

        Firebase.auth.currentUser?.let {
            viewmodel.loadUser()
            viewmodel.observeGeneralRoom()

            binding.signoutButton.setOnClickListener {
                viewmodel.signout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            viewmodel.messagesState.observe(this){
                adapter.messages = it
                adapter.notifyItemInserted(it.lastIndex)
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
                it.profilePic?.let {
                    Glide.with(this@ProfileActivity).load(it).into(binding.imageView)
                }
                Log.e(">>>", it.profilePic.toString())
            }



        } ?: run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }



}