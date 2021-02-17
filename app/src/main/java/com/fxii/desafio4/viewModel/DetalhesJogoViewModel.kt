package com.fxii.desafio4.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxii.desafio4.business.DetalhesJogoBusiness
import com.fxii.desafio4.model.Jogo
import kotlinx.coroutines.launch

class DetalhesJogoViewModel: ViewModel() {
    val jogo: MutableLiveData<Jogo> = MutableLiveData()

    private val detalhesJogoBusiness by lazy {
        DetalhesJogoBusiness()
    }

    fun getJogo(jogoId: String) {
        viewModelScope.launch {
            jogo.postValue(detalhesJogoBusiness.getJogo(jogoId))
        }
    }
}