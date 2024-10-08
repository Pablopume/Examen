package com.example.plantillaexamen.data.sources.service

import com.example.plantillaexamen.data.model.OrderResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("order")
    suspend fun getOrders(): Response<List<OrderResponse>>

    @DELETE("order/{id}")
    suspend fun deleteOrder(@Path("id") id: Int): Response<ResponseBody>

    @POST("order")
    suspend fun createOrder(@Body order: OrderResponse): Response<OrderResponse>


}