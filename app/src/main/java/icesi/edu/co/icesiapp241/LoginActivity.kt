package icesi.edu.co.icesiapp241

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import icesi.edu.co.icesiapp241.databinding.ActivityLoginBinding
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
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

        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.emailET.text.toString(),
                binding.passET.text.toString(),
            )
        }

        viewModel.authStatus.observe(this){
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java)
                    )
                }
            }
        }

    }
}