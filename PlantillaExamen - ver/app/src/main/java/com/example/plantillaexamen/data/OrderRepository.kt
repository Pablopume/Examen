package com.example.plantillaexamen.data


import com.example.plantillaexamen.data.model.toOrder
import com.example.plantillaexamen.data.sources.service.OrderService
import com.example.plantillaexamen.utils.NetworkResult
import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.domain.modelo.toOrderResponse

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
                } ?: NetworkResult.Error("No se ha podido crear")
            } else {
                val errorBodyString = response.errorBody()?.string() ?: "Unknow Error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error ocurred")
        }
    }

    suspend fun deleteOrder(id: Int): NetworkResult<String> {
        val nt: NetworkResult<String>
        val response = orderService.deleteOrder(id)
        nt = if (response.isSuccessful) {
            val responseBodyString = response.body()?.string() ?: "Deleted Succesfully"
            NetworkResult.Success(responseBodyString)
        } else {
            val errorBodyString = response.errorBody()?.string() ?: "Unknow Error"
            NetworkResult.Error(errorBodyString)
        }
        return nt
    }
}