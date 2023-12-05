package com.example.restaurantapi.data.model

import com.example.restaurantapi.data.Constants
import com.example.restaurantapi.domain.modelo.Customer
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class CustomerResponse(
@SerializedName(Constants.id)
val id: Int,
@SerializedName(Constants.firstName)
val name: String,
@SerializedName(Constants.lastName)
val lastName: String,
@SerializedName(Constants.email)
val email: String,
@SerializedName(Constants.phone)
val phone: String,
@SerializedName(Constants.dob)
val dob: String,
)

fun CustomerResponse.toCustomer() : Customer = Customer(id, name, lastName, email, phone,LocalDate.parse(dob) )
