package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.ui.repository.SignUpRepository

class SignUpViewModel : ViewModel() {

    private val repository = SignUpRepository()

    val signUpState : LiveData<DataState<SignUp>> = repository.dataState

    fun requestSignUp (name : String, phone : String,email : String,password : String) {
        repository.requestSignUp(name, phone, email, password)
    }
}