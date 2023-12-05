package com.example.restaurantapi.framework.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapi.domain.modelo.Customer
import com.example.restaurantapi.domain.usecases.DeleteCustomerUseCase
import com.example.restaurantapi.domain.usecases.GetAllCustomersUseCase
import com.example.restaurantapi.framework.ConstantsFramework
import com.example.restaurantapi.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
    private val getAllCustomersUseCase: GetAllCustomersUseCase
) : ViewModel() {


    private val customerList = mutableListOf<Customer>()
    private val _error = MutableLiveData<String>()
    private var selectedCustomers = mutableListOf<Customer>()
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState


    init {

        _uiState.value = MainState(
            customers = emptyList(),
            selectedCustomers = emptyList(),
            selectMode = false
        )
        getCustomers()


    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetCustomers -> {
                getCustomers()
            }

            MainEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)


            is MainEvent.DeleteCustomers -> {
                deleteCustomer(uiState.value?.selectedCustomers ?: emptyList())
                resetSelectMode()
            }

            is MainEvent.SelectCustomer -> selectCustomer(event.customer)

            is MainEvent.DeleteCustomer -> {
                deleteCustomer(event.customer)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()

            MainEvent.StartSelectMode -> _uiState.value = _uiState.value?.copy(selectMode = true)
        }
    }

    private fun resetSelectMode() {
        selectedCustomers.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false, selectedCustomers = selectedCustomers)

    }

    private fun getCustomers() {
        viewModelScope.launch {
            when (val result = getAllCustomersUseCase.invoke()) {
                is NetworkResult.Error<*> -> _error.value =
                    result.message ?: ConstantsFramework.error

                is NetworkResult.Success<*> -> {
                    if (result.data is List<*>) {
                        customerList.clear()
                        (result.data as List<*>).forEach { item ->
                            if (item is Customer) {
                                customerList.add(item)
                            }
                        }
                    }
                }
            }
            _uiState.value = _uiState.value?.copy(customers = customerList)
        }
    }





    private fun deleteCustomer(customer: List<Customer>) {
        viewModelScope.launch {
            val customerCopy = customer.toList()
            val customersForDelete = mutableListOf<Customer>()
            var isSuccessful = true
            for (customer2 in customerCopy) {
                val result = deleteCustomerUseCase.invoke(customer2)
                if (result is NetworkResult.Error<*>) {
                    _error.value = ConstantsFramework.ERROR_AL_BORRAR
                    isSuccessful = false

                } else {
                    customersForDelete.add(customer2)
                }
            }


            if (isSuccessful) {
                customerList.removeAll(customersForDelete)
                selectedCustomers.removeAll(customersForDelete)
                _uiState.value =
                    _uiState.value?.copy(selectedCustomers = selectedCustomers.toList())
            }


            getCustomers()
        }
    }

    private fun deleteCustomer(customer: Customer) {

        deleteCustomer(listOf(customer))

    }


    private fun selectCustomer(customer: Customer) {

        if (isSelected(customer)) {
            selectedCustomers.remove(customer)
        } else {
            selectedCustomers.add(customer)
        }
        _uiState.value = _uiState.value?.copy(selectedCustomers = selectedCustomers)

    }

    private fun isSelected(customer: Customer): Boolean {
        return selectedCustomers.contains(customer)
    }
}