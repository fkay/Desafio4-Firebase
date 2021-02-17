package com.fxii.desafio4.repositories

import android.net.Uri
import android.util.Log
import com.fxii.desafio4.model.Jogo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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

    private val storage by lazy {
        Firebase.storage
    }

    suspend fun getJogo(uid: String, jogoId: String): Jogo? {
        try {
            val data = db.collection("usuarios")
                    .document(uid)
                    .collection("jogos")
                    .document(jogoId)
                    .get()
                    .await()

            val jogo = data?.toObject<Jogo>()
            jogo?.imagem = getJogoCapa(uid, jogoId)
            return jogo
        } catch(e: Exception) {
            Log.w("Teste", "Falha ao ler jogo ${jogoId}", e)
            return null
        }
    }

    suspend fun getJogos(uid: String): List<Jogo> {
        try {
            val data = db.collection("usuarios")
                .document(uid)
                .collection("jogos")
                .get()
                .await()

            val jogos = data?.toObjects<Jogo>() ?: emptyList()
            jogos.map{ jogo ->
                jogo.imagem = getJogoCapa(uid, jogo.id ?: "")
            }
            return jogos
        } catch(e: Exception) {
            Log.w("Teste", "Falha ao ler jogos", e)
            throw Exception("Falha ao ler jogos")
        }
    }

    suspend fun getJogoCapa(uid: String, jogoId: String): Uri?  {
        return try {
            val fileRef = storageRef.child("${uid}/${jogoId}.jpg")
            fileRef.downloadUrl.await()
        } catch(e: Exception) {
            Log.w("Teste", "Falha ao ler imagem do jogo", e)
            null
        }
    }

    suspend fun saveJogo(jogo: Jogo, uid: String) {
        try {
            val data = db.collection("usuarios")
                .document(uid)
                .collection("jogos")
                .document(jogo.id ?: "")
                .set(jogo)
                .await()

            // salva imagem no store
            val fileRef = storageRef.child("${uid}/${jogo.id}.jpg")

            jogo.imagem?.let {
                fileRef.putFile(it).await()
            }

        } catch(e: Exception) {
            throw(Exception("Falha ao gravar o jogo - ${e.localizedMessage}"))
        }

    }
}