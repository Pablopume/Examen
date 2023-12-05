package com.example.actoresrecyclerview.ui.pantallamain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.actoresrecyclerview.domain.usecases.AddActorUseCase
import com.example.actoresrecyclerview.domain.usecases.DeleteActorUseCase
import com.example.actoresrecyclerview.domain.usecases.GetActorIdUseCase
import com.example.actoresrecyclerview.domain.usecases.UpdateActorUseCase
import com.example.actoresrecyclerview.utils.StringProvider
import com.example.actoresrecyclerview.domain.modelo.Actores
import com.example.actoresrecyclerview.domain.usecases.ActorRepetidoUseCase
import com.example.actoresrecyclerview.domain.usecases.ActoresEmptyUseCase
import com.example.actoresrecyclerview.ui.Constantes
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding

class ActoresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            AddActorUseCase(),
            DeleteActorUseCase(),
            GetActorIdUseCase(),
            UpdateActorUseCase(),
            ActoresEmptyUseCase(),
            ActorRepetidoUseCase(),
            StringProvider(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        val intent = intent
        if (intent.hasExtra(Constantes.ID_ELEMENTO)) {
            val id = intent.getIntExtra(Constantes.ID_ELEMENTO, 0)
            viewModel.getActor(id)
        }
        observeViewModel()
        metodos()
        observeViewModel()
    }

    private fun metodos() {
        with(binding) {
            imageButtonBin.setOnClickListener {
                viewModel.deleteActor()
                finish()

            }
            addButton.setOnClickListener {
                if (textName.text.isNullOrEmpty() || peliculaFam.text.isNullOrEmpty()) {
                    Toast.makeText(
                        this@ActoresActivity,
                        Constantes.RELLENA,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val name: String = textName.text.toString()
                    val slide: Int = slider2.value.toInt()
                    var genero: String = ""
                    when (radioGroup.checkedRadioButtonId) {
                        radioButtonHombre.id -> genero = radioButtonHombre.text.toString()
                        radioButtonMujer.id -> genero = radioButtonMujer.text.toString()
                        radioButtonOtro.id -> genero = radioButtonOtro.text.toString()
                    }
                    val vivo: Boolean = checkBox.isChecked
                    val peli: String = peliculaFam.text.toString()
                    var actor = Actores(1, name, vivo, peli, slide, genero)
                    viewModel.addActor(actor)

                }
            }
            updateButton.setOnClickListener {
                val name: String = textName.text.toString()
                val slide: Int = slider2.value.toInt()
                var genero: String = Constantes.EMPTY
                when (radioGroup.checkedRadioButtonId) {
                    radioButtonHombre.id -> genero = radioButtonHombre.text.toString()
                    radioButtonMujer.id -> genero = radioButtonMujer.text.toString()
                    radioButtonOtro.id -> genero = radioButtonOtro.text.toString()
                }
                val vivo: Boolean = checkBox.isChecked
                val peli: String = peliculaFam.text.toString()

                var actor = Actores(0, name, vivo, peli, slide, genero)
                viewModel.updateActor(actor)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this@ActoresActivity) { state ->
            state.error?.let { error ->
                Toast.makeText(this@ActoresActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }

            if (state.error == null) {

                with(binding) {

                    textName.setText(state.actores.nombre)

                    when (state.actores.genero) {
                        radioButtonHombre.text -> radioButtonHombre.isChecked = true
                        radioButtonMujer.text -> radioButtonMujer.isChecked = true
                        radioButtonOtro.text -> radioButtonOtro.isChecked = true
                    }
                    slider2.value = state.actores.premiosOscar.toFloat()
                    checkBox.isChecked = state.actores.vivo
                    peliculaFam.setText(state.actores.peliculaFamosa)
                }

            }

        }
    }
}