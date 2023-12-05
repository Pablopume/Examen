package com.example.restaurantapi.data.model

import com.example.restaurantapi.data.Constants
import com.example.restaurantapi.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OrderResponse (
    @SerializedName(Constants.id)
    val id: Int,
    @SerializedName(Constants.customerId)
    val customerId: Int,
    @SerializedName(Constants.orderDate)
    val orderDate: String,
    @SerializedName(Constants.tableId)
    val tableId: Int,
)
fun OrderResponse.toOrder() : Order = Order(id, customerId, LocalDate.parse(orderDate), tableId )