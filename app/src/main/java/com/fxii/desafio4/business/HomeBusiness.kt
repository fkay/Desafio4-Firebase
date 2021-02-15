package com.fxii.desafio4.business

import com.fxii.desafio4.model.Jogo
import com.fxii.desafio4.repositories.JogosRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeBusiness {
    private val jogosRepository by lazy {
        JogosRepository()
    }

    private val auth by lazy {
        Firebase.auth
    }

    suspend fun getJogos(): List<Jogo> {
        auth.currentUser?.let {
            try {
                return jogosRepository.getJogos(it.uid)
            } catch(e: Exception) {
                throw e
            }
        }
        return emptyList()
    }
}