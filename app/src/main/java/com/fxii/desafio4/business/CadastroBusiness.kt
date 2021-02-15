package com.fxii.desafio4.business

import android.util.Log
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CadastroBusiness {
    private val auth by lazy {
        Firebase.auth
    }

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            // Sign in success, update UI with the signed-in user's information
            Log.i("Teste", "createUserWithEmail:success")
            return authResult?.user
        } catch (e: Exception) {
            // If sign in fails, display a message to the user.
            Log.w("Teste", "createUserWithEmail:failure", e)
            val message = when (e) {
                is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                is FirebaseAuthWeakPasswordException -> e.localizedMessage
                else -> "Falha ao registrar o usuário"
            }
            throw(Exception(message))
        }
    }

    suspend fun completarRegistro(
        user: FirebaseUser, nome: String
    ): Boolean {

        try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build()

            user.updateProfile(profileUpdates).await()
            Log.i("Teste", "User profile updated.")
            return true
        } catch (e: Exception) {
            throw(Exception("Falha ao gravar dados do usuário"))
        }
    }
}