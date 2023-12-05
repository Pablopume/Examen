package com.example.restaurantapi.data

import com.example.restaurantapi.data.model.toCustomer
import com.example.restaurantapi.domain.modelo.Customer
import com.example.restaurantapi.data.sources.service.CustomerService
import com.example.restaurantapi.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.time.LocalDate
import javax.inject.Inject



@ActivityRetainedScoped
class CustomerRepository @Inject constructor(private val customerService: CustomerService) {

    suspend fun getCustomer(id: Int): NetworkResult<Customer> {
        var networkresult: NetworkResult<Customer>
        networkresult = NetworkResult.Success(Customer(1, Constants.empty, Constants.empty, Constants.empty, Constants.empty, LocalDate.now()))
        val result = customerService.getCustomer(id)

        if (result.isSuccessful) {
            val customerResponse = result.body()
            if (customerResponse != null) {
                networkresult = NetworkResult.Success(customerResponse.toCustomer())
            }
        } else {
            networkresult =
                NetworkResult.Error(result.errorBody()?.string() ?: Constants.UNKNOWN_ERROR)
        }
        return networkresult
    }


    suspend fun getCustomers(): NetworkResult<List<Customer>> {
        var l: List<Customer> = emptyList()
        customerService.getCustomers().body()?.let {
            l = it.map { customerResponse ->
                customerResponse.toCustomer()
            }
        }
        return NetworkResult.Success(l)
    }

    suspend fun deleteCustomer(id: Int): NetworkResult<String> {
        return try {
            val response = customerService.deleteCustomer(id)
            if (response.isSuccessful) {

                val responseBodyString = response.body()?.string() ?: Constants.DELETED_SUCCESSFULLY
                NetworkResult.Success(responseBodyString)
            } else {

                val errorBodyString = response.errorBody()?.string() ?: Constants.UNKNOWN_ERROR
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.AN_ERROR_OCCURRED)
        }
    }
}