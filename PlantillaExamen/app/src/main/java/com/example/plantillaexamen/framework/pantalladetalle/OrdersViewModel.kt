package com.example.plantillaexamen.framework.pantalladetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantillaexamen.domain.usecases.AddOrderUseCase
import com.example.plantillaexamen.domain.usecases.DeleteOrderUseCase
import com.example.plantillaexamen.domain.usecases.GetAllOrdersUseCase
import com.example.plantillaexamen.domain.usecases.GetCustomerUseCase
import com.example.plantillaexamen.utils.NetworkResult
import com.example.restaurantapi.domain.modelo.Customer
import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.framework.pantallarorders.OrderEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getCustomerUseCase: GetCustomerUseCase,
    private val addOrderUseCase: AddOrderUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase
) : ViewModel() {
    private val listOrders = mutableListOf<Order>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _uiState = MutableLiveData(OrdersState())
    val uiState: LiveData<OrdersState> get() = _uiState


    init {
        _uiState.value = OrdersState(
            personas = emptyList(),
            customerActual = null
        )
    }

    fun handleEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.GetOrders -> {
                getOrders(event.id)
            }

            is OrderEvent.GetCustomer -> {
                getCustomer(event.id)
            }

            is OrderEvent.AddOrder -> {
                addOrder(event.order)
            }
            OrderEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)
            is OrderEvent.DeletePersona -> {
                deleteOrder(event.order)
            }
        }
    }

    private fun getCustomer(id: Int) {
        viewModelScope.launch {
            when (val result = getCustomerUseCase.invoke(id)) {
                is NetworkResult.Error<*> -> _error.value =
                    result.message ?: "error"
                is NetworkResult.Success<*> -> {
                    _uiState.value = _uiState.value?.copy(customerActual = result.data as Customer)

                }
            }
        }
    }




    private fun addOrder(order: Order) {
        viewModelScope.launch {
            when (val result = addOrderUseCase.invoke(order)) {
                is NetworkResult.Error<*> -> _error.value =
                    result.message ?: "error"

                is NetworkResult.Success<*> -> {
                    if (result.data is Order) {
                        getOrders(order.customerId)
                    }
                }
            }
        }
    }

    private fun getOrders(id: Int) {
        viewModelScope.launch {
            val result = getAllOrdersUseCase.invoke(id)
            listOrders.clear()
            listOrders.addAll(result)
            _uiState.value = _uiState.value?.copy(personas = listOrders)
        }

    }


    private fun deleteOrder(orders: List<Order>) {
        viewModelScope.launch {
            val copiaPersonas = orders.toList()
            val personasParaEliminar = mutableListOf<Order>()
            var isSuccessful = true
            for (persona in copiaPersonas) {
                val result = deleteOrderUseCase.invoke(persona)
                if (result is NetworkResult.Error<*>) {
                    _error.value = "Error al eliminar"
                    isSuccessful = false

                } else {
                    personasParaEliminar.add(persona)
                }
            }
            if (isSuccessful) {
                listOrders.removeAll(personasParaEliminar)

                _uiState.value =
                    _uiState.value?.copy(
                        personas = listOrders
                    )
            }
        }
    }

    private fun deleteOrder(persona: Order) {

        deleteOrder(listOf(persona))

    }





}