package com.fxii.desafio4.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxii.desafio4.business.LoginBusiness
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val erroMsg: MutableLiveData<String> = MutableLiveData()

    private val loginBusiness by lazy {
        LoginBusiness()
    }

    init {
        validateUser()
    }

    fun validateUser() {
        loginBusiness.getCurrentUser()?.let {
            user.postValue(it)
        }
    }

    fun loginEmailSenha(email: String, senha: String) {
        viewModelScope.launch {
            try {
                user.postValue(loginBusiness.loginEmailSenha(email, senha))
            } catch (e: Exception) {
                erroMsg.postValue(e.message)
            }
        }
    }
}