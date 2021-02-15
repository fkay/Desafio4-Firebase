package com.fxii.desafio4.business

import com.fxii.desafio4.model.Jogo
import com.fxii.desafio4.repositories.JogosRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditarJogoBusiness {
    private val jogosRepository by lazy {
        JogosRepository()
    }

    private val auth by lazy {
        Firebase.auth
    }

    suspend fun saveJogo(jogo: Jogo) {
        try {
            jogosRepository.saveJogo(jogo, auth.currentUser?.uid ?: "")
        } catch (e: Exception) {
            throw e
        }
    }
}