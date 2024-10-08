package com.example.restaurantapi.framework.pantallarorders


import com.example.restaurantapi.domain.modelo.Order

sealed class OrderEvent {


    class DeleteOrder(val order:Order) : OrderEvent()
    class AddOrder(val order: Order) : OrderEvent()
    class GetCustomer(val id: Int) : OrderEvent()
    class GetOrders(val id: Int) : OrderEvent()
    object ErrorVisto : OrderEvent()



}
