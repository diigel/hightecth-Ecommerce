package com.hightech.ecommerce.data.response

data class SignInResponse(
    val code: Int,
    val `data`: DataSigInResponse? = null,
    val message: String,
    val status: Boolean
) {
    data class DataSigInResponse(
        val created_at: String,
        val device_dev: String,
        val device_id: String,
        val expired_at: String,
        val fcm_token: String,
        val id: Int,
        val latitude: String,
        val longitude: String,
        val status: String,
        val token: String,
        val updated_at: String,
        val user: UserSigInResponse,
        val user_id: Int
    ) {
        data class UserSigInResponse(
            val created_at: String,
            val email: String,
            val id: Int,
            val name: String,
            val phone: String,
            val status: String,
            val updated_at: String
        )
    }
}