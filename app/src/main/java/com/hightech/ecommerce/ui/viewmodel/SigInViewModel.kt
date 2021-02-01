package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.data.SignIn
import com.hightech.ecommerce.ui.repository.SignInRepository

class SigInViewModel : ViewModel() {

    private val repository = SignInRepository()

    val signIn : LiveData<SignIn> = repository.signInModel

    fun requestSign(email : String,password : String,deviceUniqId : String,token :String) {
         repository.requestSignIn(email, password,deviceUniqId, token)
    }
}