package com.fxii.desafio4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fxii.desafio4.databinding.ActivityDetalhesJogoBinding

class DetalhesJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesJogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservables()
    }

    private fun setupObservables() {
        binding.btnDetalhesEditar.setOnClickListener {
            val intent = Intent(this, EditarJogoActivity::class.java)
            // tem um putExtra aqui
            startActivity(intent)
        }

        binding.btnDetalhesVoltar.setOnClickListener {
            finish()
        }
    }
}