package com.example.actoresrecyclerview.ui.pantallarecycled

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.actoresrecyclerview.domain.usecases.GetActoresUseCase
import com.example.actoresrecyclerview.data.RepositoryActores
import com.example.actoresrecyclerview.ui.pantallamain.ActoresActivity
import com.example.actoresrecyclerview.ui.Constantes
import com.example.recyclerview.databinding.ActivityReciclerBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReciclerBinding
    private lateinit var adapter: ActoresAdapter
    private val viewModel: MainViewModel by viewModels {
        RecycledViewModelFactory(
            getActoresUseCase = GetActoresUseCase(RepositoryActores(assets.open(Constantes.DATA_JSON)))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReciclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            imageView.load(Constantes.LINK_CHADLER)
        }

        val listaPersonas = viewModel.uiState.value?.lista ?: emptyList()
        adapter = ActoresAdapter(listaPersonas, ::click)
        listaPersonas.let {
            binding.rvPersonas.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        observarViewModel()

    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this) { state ->
            state?.let {
                adapter.cambiarLista(it.lista)
            }
        }

    }

    private fun click(id: Int) {
        val intent = Intent(this, ActoresActivity::class.java).apply {
            putExtra(Constantes.ID_ELEMENTO, id)
        }
        startActivity(intent)
    }
}