package com.fxii.desafio4.model

import com.google.firebase.firestore.DocumentId

data class Jogo(
    @DocumentId
    val id: String? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val lancamento: Int? = null
)
