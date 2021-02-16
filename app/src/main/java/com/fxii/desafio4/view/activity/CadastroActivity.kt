package com.fxii.desafio4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ActivityCadastroBinding
import com.fxii.desafio4.extensions.validateEmailValue
import com.fxii.desafio4.extensions.validateFieldValue
import com.fxii.desafio4.extensions.validateRequiredField
import com.fxii.desafio4.viewModel.CadastroViewModel

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding

    private lateinit var cadastroViewModel: CadastroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cadastroViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        setupObservables()
    }

    private fun setupObservables() {
        binding.btnCadastroCriarConta.setOnClickListener {
            with(binding) {
                val password = edCadastroSenha.text.toString()


                // validaçoes dos campos
                val checkResult = tfCadastroNome.validateRequiredField(R.string.name)
                    .and(tfCadastroEmail.validateRequiredField(R.string.email))
                    .and(tfCadastroSenha.validateRequiredField(R.string.password))
                    .and(tfCadastroRepeteSenha.validateRequiredField(R.string.repeat_password))
                    .and(tfCadastroRepeteSenha.validateFieldValue("Passwords mismatch", password))
                    .and(tfCadastroEmail.validateEmailValue())

                if(checkResult) {
                    val email = edCadastroEmail.text.toString()
                    val name = edCadastroNome.text.toString()
                    cadastroViewModel.registerUser(email, password, name)
                }

            }
        }

        cadastroViewModel.erroMsg.observe(this) {
            Toast.makeText(baseContext, it,
                Toast.LENGTH_SHORT).show()
        }

        cadastroViewModel.cadastroFinalizado.observe(this) {
            if(it) {
                // termina a atividade e volta ao login, que quando tiver usuario logado segue para a home
                finish()
            }
        }

    }
}