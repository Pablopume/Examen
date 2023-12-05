package com.example.restaurantapi.data.sources.service

import com.example.restaurantapi.data.Constants
import com.example.restaurantapi.data.model.CustomerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface CustomerService {

    @GET(Constants.customer)
    suspend fun getCustomers(): Response<List<CustomerResponse>>

    @DELETE(Constants.customerIdRoute)
    suspend fun deleteCustomer(@Path(Constants.id) id: Int): Response<ResponseBody>

    @GET(Constants.customerIdRoute)
    suspend fun getCustomer(@Path(Constants.id) id: Int): Response<CustomerResponse>
}