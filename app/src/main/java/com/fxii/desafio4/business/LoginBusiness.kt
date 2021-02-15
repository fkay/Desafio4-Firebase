package com.fxii.desafio4.business

import android.util.Log
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginBusiness {
    private val auth by lazy {
        Firebase.auth
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun loginEmailSenha(email: String, senha: String): FirebaseUser? {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, senha)
                .await()

            // Sign in success, update UI with the signed-in user's information
            Log.i("Teste", "signInWithEmail:success")
            return authResult?.user

        } catch (e: Exception) {
            Log.w("Teste", "signInWithEmail:failure", e)

            val message = when (e) {
                is FirebaseAuthInvalidCredentialsException -> "Senha inválida"
                is FirebaseAuthInvalidUserException -> "Usuário inválido"
                else -> "Falha na autenticação"
            }
            throw(Exception(message))
        }
    }
}