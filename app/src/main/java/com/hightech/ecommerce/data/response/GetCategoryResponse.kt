package com.hightech.ecommerce.data.response

data class GetCategoryResponse(
    val code: Int,
    val `data`: List<Data>? = null,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val created_at: String,
        val detail: String,
        val id: Int,
        val name: String,
        val status: String,
        val updated_at: String
    )
}