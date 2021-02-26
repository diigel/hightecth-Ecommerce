package com.hightech.ecommerce.data.response

data class AddAddressResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val address: String,
        val city_id: String,
        val city_name: String,
        val created_at: String,
        val email: String,
        val id: Int,
        val name: String,
        val phone: String,
        val postal_code: String,
        val province_id: String,
        val province_name: String,
        val updated_at: String,
        val user_id: Int
    )
}