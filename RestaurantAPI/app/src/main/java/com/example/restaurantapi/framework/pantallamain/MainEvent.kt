package com.example.restaurantapi.framework.pantallamain


import com.example.restaurantapi.domain.modelo.Customer

sealed class MainEvent {

    class DeleteCustomers() : MainEvent()
    class DeleteCustomer(val customer:Customer) : MainEvent()
    class SelectCustomer(val customer: Customer) : MainEvent()

    object GetCustomers : MainEvent()
    object ErrorVisto : MainEvent()
    object StartSelectMode: MainEvent()
    object ResetSelectMode: MainEvent()
}
