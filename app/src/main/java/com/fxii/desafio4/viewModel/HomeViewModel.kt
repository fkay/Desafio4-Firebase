package com.fxii.desafio4.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxii.desafio4.business.HomeBusiness
import com.fxii.desafio4.model.Jogo
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val jogos: MutableLiveData<List<Jogo>> = MutableLiveData()
    val errMessage: MutableLiveData<String> = MutableLiveData()

    private val homeBusiness by lazy {
        HomeBusiness()
    }

    fun getJogos() {
        viewModelScope.launch {
            try {
                jogos.postValue(homeBusiness.getJogos())
            } catch(e: Exception) {
                errMessage.postValue(e.message)
            }
        }
    }
}