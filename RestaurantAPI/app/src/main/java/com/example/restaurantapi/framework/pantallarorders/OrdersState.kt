package com.example.restaurantapi.framework.pantallarorders

import com.example.restaurantapi.domain.modelo.Customer
import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.framework.ConstantsFramework
import java.time.LocalDate



data class OrdersState (val orders: List<Order> = emptyList(),
                        val selectedOrders: List<Order> = emptyList(),
                        val error: String? = null,
                        val actualCustomer: Customer? = Customer(0,
                            ConstantsFramework.empty,
                            ConstantsFramework.empty,
                            ConstantsFramework.empty,
                            ConstantsFramework.empty, LocalDate.now(),isSelected = false)  )