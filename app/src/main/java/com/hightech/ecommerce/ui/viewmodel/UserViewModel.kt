package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.GetUserResponse
import com.hightech.ecommerce.data.response.UpdateProfileResponse
import com.hightech.ecommerce.ui.repository.UserRepository

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    val getUser : LiveData<DataState<GetUserResponse>> = repository.getUserResponse
    val updateProfile : LiveData<DataState<UpdateProfileResponse>> = repository.updateUser

    fun getUser(){
        repository.getUser()
    }

    fun requestUpadteProfile(name :String,phoneNumber : String,email : String,password : String){
        repository.updateUser(name, phoneNumber, email, password)
    }
}