package com.example.actoresrecyclerview.ui.pantallarecycled

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actoresrecyclerview.domain.usecases.GetActoresUseCase
import com.example.actoresrecyclerview.ui.Constantes


class MainViewModel(getActoresUseCase: GetActoresUseCase) : ViewModel() {
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            lista = getActoresUseCase()
        )
    }
}

class RecycledViewModelFactory(
    private val getActoresUseCase: GetActoresUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getActoresUseCase) as T
        }
        throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
    }

}
