package com.hightech.ecommerce.data

data class SignUp (
    val status: Boolean,
    val message: String,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null
)