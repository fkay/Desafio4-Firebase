package com.fxii.desafio4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ActivityLoginBinding
import com.fxii.desafio4.extensions.validateRequiredField
import com.fxii.desafio4.viewModel.LoginViewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setupObservables()
    }

    override fun onResume() {
        super.onResume()
        // verifica se tem usuario logado
        loginViewModel.validateUser()
    }

    private fun setupObservables() {
        binding.btnLoginRegistrar.setOnClickListener {
            intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginLogin.setOnClickListener {
            val checkValidates = binding.tfLoginEmail.validateRequiredField(R.string.email)
                .and(binding.tfLoginSenha.validateRequiredField(R.string.password))

            if(checkValidates) {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginSenha.text.toString()
                loginViewModel.loginEmailSenha(email, password)
            }
        }

        loginViewModel.user.observe(this) { user ->
            user?.let {
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.erroMsg.observe(this) { erroMsg ->
            Toast.makeText(baseContext, erroMsg,
                Toast.LENGTH_SHORT).show()
        }
    }
}