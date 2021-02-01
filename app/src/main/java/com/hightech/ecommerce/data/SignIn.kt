package com.hightech.ecommerce.data

data class SignIn(
    val userId: Int? = null,
    val token: String? = null,
    val deviceId: String? = null,
    val deviceDev: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val userName: String? = null,
    val userPhone: String? = null,
    val userEmail: String? = null,
    val message : String?,
    val status : Boolean
)
