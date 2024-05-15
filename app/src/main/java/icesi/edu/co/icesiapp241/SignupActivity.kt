package icesi.edu.co.icesiapp241

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.icesiapp241.databinding.ActivitySignupBinding
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.User
import icesi.edu.co.icesiapp241.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.igotaccountBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.signupBtn.setOnClickListener {
            viewModel.signup(
                User(
                    "",
                    binding.usernameET.text.toString(),
                    binding.emailET.text.toString(),
                    binding.nameET.text.toString()
                ),
                binding.passET.text.toString()
            )
        }

        viewModel.authStatus.observe(this) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity(
                        Intent(this@SignupActivity, ProfileActivity::class.java)
                    )
                }
            }
        }

    }
}