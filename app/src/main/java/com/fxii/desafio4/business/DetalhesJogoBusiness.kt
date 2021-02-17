package com.fxii.desafio4.business

import com.fxii.desafio4.model.Jogo
import com.fxii.desafio4.repositories.JogosRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetalhesJogoBusiness {
    private val jogosRepository by lazy {
        JogosRepository()
    }

    private val auth by lazy {
        Firebase.auth
    }

    suspend fun getJogo(jogoId: String): Jogo? {
        auth.currentUser?.let { user ->
            return jogosRepository.getJogo(user.uid, jogoId)
        }
        return null
    }
}