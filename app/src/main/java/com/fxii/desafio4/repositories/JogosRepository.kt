package com.fxii.desafio4.repositories

import android.net.Uri
import android.util.Log
import com.fxii.desafio4.model.Jogo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class JogosRepository {
    val db by lazy {
        Firebase.firestore
    }

    private val storageRef by lazy {
        Firebase.storage.reference
    }

    suspend fun getJogos(uid: String): List<Jogo> {
        try {
            val data = db.collection("usuarios")
                .document(uid)
                .collection("jogos")
                .get()
                .await()

            return data?.toObjects<Jogo>() ?: emptyList()
        } catch(e: Exception) {
            Log.w("Teste", "Falha ao ler jogos", e)
            throw Exception("Falha ao ler jogos")
        }
    }

    suspend fun saveJogo(jogo: Jogo, uid: String, imagem: Uri) {
        try {
            val data = db.collection("usuarios")
                .document(uid)
                .collection("jogos")
                .document(jogo.id ?: "")
                .set(jogo)
                .await()

            // salva imagem no store
            val fileRef = storageRef.child("${uid}/${jogo.id}.jpg")

            fileRef.putFile(imagem).await()

        } catch(e: Exception) {
            throw(Exception("Falha ao gravar o jogo - ${e.localizedMessage}"))
        }

    }
}