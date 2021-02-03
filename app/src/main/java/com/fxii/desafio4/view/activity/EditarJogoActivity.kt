package com.fxii.desafio4.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener
import android.util.Log
import com.fxii.desafio4.databinding.ActivityEditarJogoBinding

class EditarJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarJogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservables()
    }

    private fun setupObservables() {
        binding.btnEditarJogoSalvar.setOnClickListener {
            // salva o jogo
            finish()
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