package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.CategoryItem
import com.hightech.ecommerce.ui.repository.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    val category : LiveData<DataState<List<CategoryItem>>> = repository.category

    fun getCategory(){
        repository.getCategory()
    }
}