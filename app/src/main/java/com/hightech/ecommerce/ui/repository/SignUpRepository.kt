package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.data.response.SignUpResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {

    private val _dataState: MutableLiveData<DataState<SignUp>> = MutableLiveData(DataState.Idle())
    val dataState : LiveData<DataState<SignUp>> = _dataState

    fun requestSignUp(name: String, phone: String, email: String, password: String) {
        _dataState.postValue(DataState.Loading())
        Network.getRoutes().signUp(name, phone, email, password).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                val data = response.body()
                if (data != null) {
                    val signUp = Mapper.mapToSignUp(data)
                    _dataState.postValue(DataState.Success(signUp))
                } else {
                    _dataState.postValue(DataState.Failed(Throwable("Data tidak di temukan")))
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
                _dataState.postValue(DataState.Failed(t))
            }
        })

    }
}