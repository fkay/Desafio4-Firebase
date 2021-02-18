package com.fxii.desafio4.view.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.fxii.desafio4.GlideApp
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ItemHomeGamesBinding
import com.fxii.desafio4.model.Jogo
import java.util.*
import kotlin.collections.ArrayList

class JogoAdapter(private val jogos: List<Jogo>,
    private val onItemJogoClicked: (String) -> Unit):RecyclerView.Adapter<JogoAdapter.ViewHolder>(), Filterable {

    var jogosFiltrado = ArrayList<Jogo>()

    init {
        jogosFiltrado = ArrayList(jogos)
    }

    class ViewHolder(val binding: ItemHomeGamesBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(jogo: Jogo, onItemJogoClicked: (String) -> Unit):Unit = with(itemView) {
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
                    onItemJogoClicked(jogo.id ?: "")
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
        holder.bind(jogosFiltrado[position], onItemJogoClicked)
    }

    override fun getItemCount(): Int {
        //return jogos.size
        return jogosFiltrado.size
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtro = constraint?.toString()?.toLowerCase(Locale.ROOT)
                if(filtro.isNullOrEmpty()) {
                    jogosFiltrado = ArrayList(jogos)
                } else {
                    val resultList = ArrayList<Jogo>()
                    for(jogo in jogos) {
                        if (jogo.nome?.toLowerCase(Locale.ROOT)?.contains(filtro) == true ||
                                jogo.descricao?.toLowerCase(Locale.ROOT)?.contains(filtro) == true) {
                            resultList.add(jogo)
                        }
                    }
                    jogosFiltrado = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = jogosFiltrado
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                jogosFiltrado = results?.values as ArrayList<Jogo>
                notifyDataSetChanged()
            }
        }
    }
}