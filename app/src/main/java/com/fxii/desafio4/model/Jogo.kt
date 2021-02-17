package com.fxii.desafio4.model

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jogo(
    @DocumentId
    val id: String? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val lancamento: Int? = null,
    @Exclude @set:Exclude @get:Exclude
    var imagem: Uri? = null
): Parcelable
