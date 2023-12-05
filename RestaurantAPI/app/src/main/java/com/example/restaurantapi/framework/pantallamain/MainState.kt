package com.example.restaurantapi.framework.pantallamain

import com.example.restaurantapi.domain.modelo.Customer

data class MainState (val customers: List<Customer> = emptyList(),
                      val selectedCustomers: List<Customer> = emptyList(),
                      val selectMode: Boolean = false,
                      val error: String? = null,)