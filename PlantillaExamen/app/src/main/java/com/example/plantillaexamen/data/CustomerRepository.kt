package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.model.toCustomer
import com.example.restaurantapi.domain.modelo.Customer
import com.example.plantillaexamen.data.sources.service.CustomerService
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.time.LocalDate
import javax.inject.Inject



@ActivityRetainedScoped
class CustomerRepository @Inject constructor(private val customerService: CustomerService) {

    suspend fun getCustomer(id: Int): NetworkResult<Customer> {
        var networkresult: NetworkResult<Customer>
        networkresult = NetworkResult.Success(Customer(1, "", "","", "", LocalDate.now()))
        val result = customerService.getCustomer(id)

        if (result.isSuccessful) {
            val customerResponse = result.body()
            if (customerResponse != null) {
                networkresult = NetworkResult.Success(customerResponse.toCustomer())
            }
        } else {
            networkresult =
                NetworkResult.Error(result.errorBody()?.string() ?: "Error desconocido")
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

                val responseBodyString = response.body()?.string() ?: "Deleted Succesfully"
                NetworkResult.Success(responseBodyString)
            } else {

                val errorBodyString = response.errorBody()?.string() ?: "Unknow Error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error ocurred")
        }
    }
}