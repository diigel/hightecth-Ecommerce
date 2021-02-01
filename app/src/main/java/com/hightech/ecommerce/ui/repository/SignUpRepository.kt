package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.data.response.SignUpResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {

    val signUpModel: MutableLiveData<SignUp> = MutableLiveData()

    fun requestSignUp(name: String, phone: String, email: String, password: String) {

        Network.getRoutes().signUp(name, phone, email, password).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                val data = response.body()
                if (data != null){
                    signUpModel.postValue(Mapper.signUp(data))
                }else{
                    signUpModel.postValue(null)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}