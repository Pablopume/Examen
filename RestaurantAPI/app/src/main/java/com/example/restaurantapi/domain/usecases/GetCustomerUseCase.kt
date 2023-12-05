package com.example.restaurantapi.domain.usecases

import com.example.restaurantapi.data.CustomerRepository
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(id: Int) = customerRepository.getCustomer(id)

}