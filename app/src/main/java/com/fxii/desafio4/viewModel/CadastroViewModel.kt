package com.fxii.desafio4.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxii.desafio4.business.CadastroBusiness
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class CadastroViewModel: ViewModel() {
    val erroMsg: MutableLiveData<String> = MutableLiveData()
    val cadastroFinalizado: MutableLiveData<Boolean> = MutableLiveData()
    var usuario: FirebaseUser? = null

    private val cadastroBusiness by lazy {
        CadastroBusiness()
    }

    init {
        cadastroFinalizado.value = false
    }

    fun registerUser(email: String, password: String,
                     nome: String) {
        viewModelScope.launch {
            try {
                val user = cadastroBusiness.registerUser(email, password)
                user?.let {
                    usuario = it
                    cadastroFinalizado.postValue(cadastroBusiness.completarRegistro(user, nome))
                }
            } catch(e: Exception) {
                erroMsg.postValue(e.message)
            }
        }
    }
}