package com.fxii.desafio4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.fxii.desafio4.GlideApp
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ActivityDetalhesJogoBinding
import com.fxii.desafio4.model.Jogo
import com.fxii.desafio4.viewModel.DetalhesJogoViewModel

class DetalhesJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesJogoBinding

    private lateinit var detalhesJogoViewModel: DetalhesJogoViewModel

    private var jogoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detalhesJogoViewModel = ViewModelProvider(this).get(DetalhesJogoViewModel::class.java)

        jogoId = intent.getStringExtra("JOGO")
        //detalhesJogoViewModel.getJogo(jogoId ?: "")

        setupObservables()
    }

    private fun setupView(jogo: Jogo) {
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

    override fun onResume() {
        super.onResume()
        jogoId?.let {
            detalhesJogoViewModel.getJogo(it)
        }
    }

    private fun setupObservables() {
        binding.btnDetalhesEditar.setOnClickListener {
            val intent = Intent(this, EditarJogoActivity::class.java)
            intent.putExtra("JOGO", detalhesJogoViewModel.jogo.value)
            startActivity(intent)
        }

        binding.btnDetalhesVoltar.setOnClickListener {
            finish()
        }

        detalhesJogoViewModel.jogo.observe(this) {
            it?.let { jogo ->
                setupView(jogo)
            }
        }
    }
}