package com.fxii.desafio4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fxii.desafio4.GlideApp
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ActivityDetalhesJogoBinding
import com.fxii.desafio4.model.Jogo

class DetalhesJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesJogoBinding

    private var jogo: Jogo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jogo = intent.getParcelableExtra<Jogo>("JOGO")

        setupView()

        setupObservables()
    }

    private fun setupView() {
        jogo?.let { jogo ->
            with(binding) {
                GlideApp.with(this@DetalhesJogoActivity)
                        .load(jogo.imagem)
                        .into(ivDetalhesCapa)

                tvDetalhesTitulo.text = jogo.nome
                tvDetalhesSubtitulo.text = jogo.nome
                tvDetalhesDescricao.text = jogo.descricao
                tvDetalhesAno.text = jogo.lancamento.toString()
            }
        }
    }

    private fun setupObservables() {
        binding.btnDetalhesEditar.setOnClickListener {
            val intent = Intent(this, EditarJogoActivity::class.java)
            intent.putExtra("JOGO", jogo)
            startActivity(intent)
        }

        binding.btnDetalhesVoltar.setOnClickListener {
            finish()
        }
    }
}