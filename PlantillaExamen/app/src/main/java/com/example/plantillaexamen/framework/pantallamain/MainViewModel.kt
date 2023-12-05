package com.example.plantillaexamen.framework.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapi.domain.modelo.Customer
import com.example.plantillaexamen.domain.usecases.DeleteCustomerUseCase
import com.example.plantillaexamen.domain.usecases.GetAllCustomersUseCase
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
    private val getAllCustomersUseCase: GetAllCustomersUseCase
) : ViewModel() {


    private val listaPersonas = mutableListOf<Customer>()
    private val _error = MutableLiveData<String>()
    private var selectedPersonas = mutableListOf<Customer>()
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState


    init {
        _uiState.value = MainState(
            personas = emptyList(),
            personasSeleccionadas = emptyList(),
            selectMode = false
        )
        getPersonas()

    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetPersonas -> {
                getPersonas()
            }

            MainEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)


            is MainEvent.DeletePersonasSeleccionadas -> {
                deletePersona(uiState.value?.personasSeleccionadas ?: emptyList())
                resetSelectMode()
            }

            is MainEvent.SeleccionaPersona -> seleccionaPersona(event.customer)
            is MainEvent.GetPersonaFiltradas -> getPersonas(event.filtro)
            is MainEvent.DeletePersona -> {
                deletePersona(event.customer)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()

            MainEvent.StartSelectMode -> _uiState.value = _uiState.value?.copy(selectMode = true)
        }
    }

    private fun resetSelectMode() {
        selectedPersonas.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false, personasSeleccionadas = selectedPersonas)

    }

    private fun getPersonas() {
        viewModelScope.launch {
            when (val result = getAllCustomersUseCase.invoke()) {
                is NetworkResult.Error<*> -> _error.value =
                    result.message ?: "error"

                is NetworkResult.Success<*> -> {
                    if (result.data is List<*>) {
                        listaPersonas.clear()
                        (result.data as List<*>).forEach { item ->
                            if (item is Customer) {
                                listaPersonas.add(item)
                            }
                        }
                    }
                }
            }
            _uiState.value = _uiState.value?.copy(personas = listaPersonas)
        }
    }


    private fun getPersonas(filtro: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                personas = listaPersonas.filter { it.name.startsWith(filtro) }.toList()
            )
        }

    }


    private fun deletePersona(personas: List<Customer>) {
        viewModelScope.launch {
            val copiaPersonas = personas.toList()
            val personasParaEliminar = mutableListOf<Customer>()
            var isSuccessful = true
            for (persona in copiaPersonas) {
                val result = deleteCustomerUseCase.invoke(persona)
                if (result is NetworkResult.Error<*>) {
                    _error.value = "Error al eliminar"
                    isSuccessful = false

                } else {
                    personasParaEliminar.add(persona)
                }
            }

            if (isSuccessful) {
                listaPersonas.removeAll(personasParaEliminar)
                selectedPersonas.removeAll(personasParaEliminar)
                _uiState.value =
                    _uiState.value?.copy(personasSeleccionadas = selectedPersonas.toList())
            }


            getPersonas()
        }
    }

    private fun deletePersona(persona: Customer) {
        deletePersona(listOf(persona))

    }


    private fun seleccionaPersona(persona: Customer) {

        if (isSelected(persona)) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        _uiState.value = _uiState.value?.copy(personasSeleccionadas = selectedPersonas)

    }

    private fun isSelected(persona: Customer): Boolean {
        return selectedPersonas.contains(persona)
    }
}