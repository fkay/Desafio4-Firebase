package com.fxii.desafio4.view.activity

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

    }
}