package com.hightech.ecommerce.data.response

data class UpdateProfileResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val created_at: String,
        val email: String,
        val id: Int,
        val name: String,
        val phone: String,
        val status: String,
        val updated_at: String
    )
}