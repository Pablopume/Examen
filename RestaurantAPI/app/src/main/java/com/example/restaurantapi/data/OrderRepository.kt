package com.example.restaurantapi.data


import com.example.restaurantapi.data.model.toOrder
import com.example.restaurantapi.data.sources.service.OrderService
import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.domain.modelo.toOrderResponse
import com.example.restaurantapi.utils.NetworkResult

import javax.inject.Inject



class OrderRepository @Inject constructor(private val orderService: OrderService) {


    suspend fun getOrdersPorId(id: Int): List<Order> {
        var list: List<Order> = getOrders().data ?: emptyList()
        list = list.filter { it.customerId == id }
        return list
    }

    suspend fun getOrders(): NetworkResult<List<Order>> {
        var list: List<Order> = emptyList()
        orderService.getOrders().body()?.let {
            list = it.map { orderResponse ->
                orderResponse.toOrder()
            }
        }
        return NetworkResult.Success(list)
    }

    suspend fun createOrder(order: Order): NetworkResult<Order> {
        return try {
            val response = orderService.createOrder(order.toOrderResponse())
            if (response.isSuccessful) {
                response.body()?.let { orderResponse ->
                    NetworkResult.Success(orderResponse.toOrder())
                } ?: NetworkResult.Error(Constants.NO_ORDER_RETURNED)
            } else {
                val errorBodyString = response.errorBody()?.string() ?: Constants.UNKNOWN_ERROR
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.AN_ERROR_OCCURRED)
        }
    }

    suspend fun deleteOrder(id: Int): NetworkResult<String> {
        val nt: NetworkResult<String>
        val response = orderService.deleteOrder(id)
        nt = if (response.isSuccessful) {
            val responseBodyString = response.body()?.string() ?: Constants.DELETED_SUCCESSFULLY
            NetworkResult.Success(responseBodyString)
        } else {
            val errorBodyString = response.errorBody()?.string() ?: Constants.UNKNOWN_ERROR
            NetworkResult.Error(errorBodyString)
        }
        return nt
    }
}