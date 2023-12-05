package com.example.restaurantapi.data.sources.service

import com.example.restaurantapi.data.Constants
import com.example.restaurantapi.data.model.OrderResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET(Constants.order)
    suspend fun getOrders(): Response<List<OrderResponse>>

    @DELETE(Constants.orderIdRoute)
    suspend fun deleteOrder(@Path(Constants.id) id: Int): Response<ResponseBody>

    @POST(Constants.order)
    suspend fun createOrder(@Body order: OrderResponse): Response<OrderResponse>


}