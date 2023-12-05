package com.example.actoresrecyclerview.ui.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class MainViewModel(
    private val addActoruseCase: AddActorUseCase,
    private val deleteActorUseCase: DeleteActorUseCase,
    private val getActorIdUseCase: GetActorIdUseCase,
    private val updateActorUseCase: UpdateActorUseCase,
    private val actoresEmptyUseCase: ActoresEmptyUseCase,
    private val actorRepetidoUseCase: ActorRepetidoUseCase,
    private val stringProvider: StringProvider

) : ViewModel() {

    private val _uiState = MutableLiveData(ActoresState())
    val uiState: LiveData<ActoresState> get() = _uiState

    init {
        _uiState.value = ActoresState(
            actores = getActorIdUseCase(1),
            error = null
        )
    }

    fun getActor(id: Int) {
        _uiState.value = _uiState.value?.copy(actores = getActorIdUseCase(id))
    }


    fun deleteActor() {
        val actores = _uiState.value?.actores
        if (actores != null && !deleteActorUseCase(actores)) {
            _uiState.value = _uiState.value?.copy(
                error = stringProvider.getString(R.string.app_name)
            )
        } else {
            if (actoresEmptyUseCase()) {
                _uiState.value = _uiState.value?.copy(actores = Actores())
            }
        }
    }


    fun addActor(actor: Actores) {
        val actores = _uiState.value?.actores
        if (!actorRepetidoUseCase(actor)) {
            if (!addActoruseCase(actor)) {
                if (actores != null) {
                    _uiState.value = _uiState.value?.copy(
                        actores = actores,
                        error = stringProvider.getString(R.string.repetido)
                    )
                }
            } else {
                _uiState.value = _uiState.value?.copy(actores = actor, error = null)
            }
        } else {
            if (actores != null) {
                _uiState.value = _uiState.value?.copy(
                    actores = actores,
                    error = stringProvider.getString(R.string.repetido)
                )
            }
        }
    }


    fun errorMostrado() {
        val actores = _uiState.value?.actores
        if (actores != null) {
            _uiState.value = _uiState.value?.copy(error = null, actores = actores)
        }
    }


    fun updateActor(actor: Actores) {
        val actores = _uiState.value?.actores
        if (actores != null) {
            updateActorUseCase(actores, actor)
        }
        _uiState.value = _uiState.value?.copy(actores = actor)
    }
}

class MainViewModelFactory(
    private val addActoruseCase: AddActorUseCase,
    private val deleteActorUseCase: DeleteActorUseCase,
    private val getActorIdUseCase: GetActorIdUseCase,
    private val updateActorUseCase: UpdateActorUseCase,
    private val actoresEmptyUseCase: ActoresEmptyUseCase,
    private val actoresRepetidoUseCase: ActorRepetidoUseCase,
    private val stringProvider: StringProvider,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addActoruseCase,
                deleteActorUseCase,
                getActorIdUseCase,
                updateActorUseCase,
                actoresEmptyUseCase,
                actoresRepetidoUseCase,
                stringProvider,
            ) as T
        }
        throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
    }
}
