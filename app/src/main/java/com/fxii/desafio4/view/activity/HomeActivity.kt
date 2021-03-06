package com.fxii.desafio4.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fxii.desafio4.databinding.ActivityHomeBinding
import com.fxii.desafio4.view.adapter.JogoAdapter
import com.fxii.desafio4.viewModel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    private var filtro: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.i("Pesquisa", query)
//                binding.rvHomeGames.adapter?.let {
//                    (it as JogoAdapter).filter.filter(query)
//                }
                binding.svHomeBuscar.setQuery(query, true)
            }
        }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupObservables()

        //homeViewModel.getJogos()
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getJogos()
    }

    private fun setupObservables() {
        homeViewModel.errMessage.observe(this) { erroMsg ->
            Toast.makeText(baseContext, erroMsg,
                Toast.LENGTH_SHORT).show()
        }

        homeViewModel.jogos.observe(this) { it ->
            it?.let { jogos ->
                binding.rvHomeGames.apply {
                    adapter = JogoAdapter(jogos) { jogoId ->
                        Log.i("Teste", "Clicou no jogo de id $jogoId")
                        // inicia a intent dos detalhes
                        val intent = Intent(this@HomeActivity, DetalhesJogoActivity::class.java)
                        intent.putExtra("JOGO", jogoId)
                        startActivity(intent)
                    }
                }
            }
        }

        // botão adicionar
        binding.fabHomeAdicionar.setOnClickListener {
            val intent = Intent(this, EditarJogoActivity::class.java)
            intent.putExtra("JOGO_ID", 1)
            startActivity(intent)
        }

        // configuração da busca
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svHomeBuscar.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.svHomeBuscar.setIconifiedByDefault(false)

        binding.svHomeBuscar.setOnCloseListener {
            Log.i("Pesquisa", "pesquisa foi limpada")
            true
        }

        binding.svHomeBuscar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val adapter = binding.rvHomeGames.adapter as JogoAdapter
                    if(it.length >= 3) {
                        adapter.filter.filter(it)
                    } else {
                        adapter.filter.filter(null)
                    }
                }
                return false
            }
        })

        binding.rvHomeGames.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view); // item position
                    val spanCount = 2;
                    val spacing = 6;//spacing between views in grid

                    if (position >= 0) {
                        val column = position % spanCount; // item column

                        outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                        outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                        if (position < spanCount) { // top edge
                            outRect.top = spacing;
                        }
                        outRect.bottom = spacing; // item bottom
                    } else {
                        outRect.left = 0;
                        outRect.right = 0;
                        outRect.top = 0;
                        outRect.bottom = 0;
                    }
                }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // ao fechar porenquanto sempre recomeca o login (falta um botao para logoff)
        Firebase.auth.signOut()
        //val intent = Intent(this, LoginActivity::class.java)
        //startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.i("Pesquisa", query ?: "sem texto")
            query?.let {
//                binding.rvHomeGames.adapter?.let {
//                    (it as JogoAdapter).filter.filter(query)
//                }
                binding.svHomeBuscar.setQuery(query, true)
            }
        }
    }
}