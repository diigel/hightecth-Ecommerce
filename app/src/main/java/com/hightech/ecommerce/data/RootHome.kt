package com.hightech.ecommerce.data


object RootHomeType{
    const val recomendasiProd = 10
    const val categoryProd = 11
}
data class RootHome(
    val type : Int,
    val recomendasiProd : List<RecomendasiProd>,
    val categoryProd : List<CategoryProd>

)

data class RecomendasiProd(
    val id : Int
)

data class CategoryProd(
    val id : Int
)