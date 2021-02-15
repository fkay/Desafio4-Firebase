package com.fxii.desafio4.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fxii.desafio4.databinding.ItemHomeGamesBinding
import com.fxii.desafio4.model.Jogo

class JogoAdapter(private val jogos: List<Jogo>,
    private val onItemJogoClicked: (Int) -> Unit):RecyclerView.Adapter<JogoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHomeGamesBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(jogo: Jogo, onItemJogoClicked: (Int) -> Unit):Unit = with(itemView) {
            // carrega imagens

            // carrega valores
            with(binding) {
                tvItemGamesTitulo.text = jogo.nome
                tvItemGamesAno.text = jogo.lancamento.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeGamesBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jogos[position], onItemJogoClicked)
    }

    override fun getItemCount(): Int {
        return jogos.size
    }
}