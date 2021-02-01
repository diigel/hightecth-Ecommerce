package com.hightech.ecommerce.data.response

data class SignUpResponse(
    val code: Int,
    val `data`: DataSignUp? = null,
    val message: String,
    val status: Boolean
) {
    data class DataSignUp(
        val created_at: String,
        val email: String,
        val id: Int,
        val name: String,
        val phone: String,
        val status: String,
        val updated_at: String
    )
}