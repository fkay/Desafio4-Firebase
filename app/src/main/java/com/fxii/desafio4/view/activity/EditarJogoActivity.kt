package com.fxii.desafio4.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fxii.desafio4.databinding.ActivityEditarJogoBinding
import com.fxii.desafio4.viewModel.EditarJogoViewModel

class EditarJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarJogoBinding

    private lateinit var editarJogoViewModel: EditarJogoViewModel

    private var jogoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editarJogoViewModel = ViewModelProvider(this).get(EditarJogoViewModel::class.java)

        setupObservables()
    }

    private fun setupObservables() {
        binding.btnEditarJogoSalvar.setOnClickListener {
            // valida campos

            // salva o jogo
            with(binding) {
                val nome = edEditarJogoNome.text.toString()
                val lancamento = edEditarJogoAno.text.toString().toInt()
                val descricao = edEditarJogoDescricao.text.toString()
                editarJogoViewModel.saveJogo(nome, descricao, lancamento)
            }
            //finish()
        }

        editarJogoViewModel.errorMsg.observe(this) { erroMsg ->
            Toast.makeText(baseContext, erroMsg,
                Toast.LENGTH_SHORT).show()
        }

        editarJogoViewModel.jogoSalvo.observe(this) {
            if(it) {
                finish()
            }
        }

        binding.fabEditarJogoCapa.setOnClickListener {
            Log.i("Teste", "Alterar imagem capa")
        }

        // mesmo inibindo ediçao o ellipsis não funcionou
//        with(binding.edEditarJogoDescricao) {
//            keyListener = null
//
//            setOnFocusChangeListener { v, hasFocus ->
//                if (hasFocus) {
//                    keyListener = TextKeyListener(TextKeyListener.Capitalize.WORDS, false)
//                } else {
//                    keyListener = null
//                }
//            }
//        }
    }
}