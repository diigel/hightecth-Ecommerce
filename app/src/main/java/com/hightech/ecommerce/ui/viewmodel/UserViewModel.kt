package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.GetUserResponse
import com.hightech.ecommerce.ui.repository.UserRepository

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    val getUser : LiveData<DataState<GetUserResponse>> = repository.getUserResponse

    fun getUser(){
        repository.getUser()
    }
}