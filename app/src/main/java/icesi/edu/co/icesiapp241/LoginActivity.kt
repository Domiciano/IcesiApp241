package icesi.edu.co.icesiapp241

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import icesi.edu.co.icesiapp241.databinding.ActivityLoginBinding
import icesi.edu.co.icesiapp241.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.noaccountButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

    }
}