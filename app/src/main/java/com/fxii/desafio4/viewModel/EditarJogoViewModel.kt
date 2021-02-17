package com.fxii.desafio4.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxii.desafio4.business.EditarJogoBusiness
import com.fxii.desafio4.model.Jogo
import kotlinx.coroutines.launch

class EditarJogoViewModel: ViewModel() {
    val errorMsg: MutableLiveData<String> = MutableLiveData()
    val jogoSalvo: MutableLiveData<Boolean> = MutableLiveData()

    init {
        jogoSalvo.value = false
    }

    private val editarJogoBusiness by lazy {
        EditarJogoBusiness()
    }

    fun saveJogo(nome: String, descricao: String, lancamento: Int, imagem: Uri?) {
        val id = nome.toLowerCase().replace(' ', '_')
        val jogo = Jogo(id = id, nome = nome, descricao = descricao, lancamento = lancamento, imagem = imagem)
        viewModelScope.launch {
            try {
                editarJogoBusiness.saveJogo(jogo)
                jogoSalvo.postValue(true)
            } catch(e: Exception) {
                errorMsg.postValue(e.message)
            }
        }
    }
}