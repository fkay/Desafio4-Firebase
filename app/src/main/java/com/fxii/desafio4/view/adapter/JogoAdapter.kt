package com.fxii.desafio4.view.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.fxii.desafio4.GlideApp
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ItemHomeGamesBinding
import com.fxii.desafio4.model.Jogo

class JogoAdapter(private val jogos: List<Jogo>,
    private val onItemJogoClicked: (Int) -> Unit):RecyclerView.Adapter<JogoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHomeGamesBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(jogo: Jogo, onItemJogoClicked: (Int) -> Unit):Unit = with(itemView) {
            with(binding) {
                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.bounds = Rect(0,0, circularProgressDrawable.intrinsicWidth, circularProgressDrawable.intrinsicHeight/2)
                circularProgressDrawable.start()

                // carrega imagens
                GlideApp.with(context)
                        .load(jogo.imagem)
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.ic_baseline_error_outline_48)
                        .into(ivItemGamesCapa)

                // carrega valores
                tvItemGamesTitulo.text = jogo.nome
                tvItemGamesAno.text = jogo.lancamento.toString()

                cvItemGamesCard.setOnClickListener {
                    onItemJogoClicked(adapterPosition)
                }
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