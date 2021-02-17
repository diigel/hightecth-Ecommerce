package com.hightech.ecommerce.ui.repository

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.SignIn
import com.hightech.ecommerce.data.response.SignInResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepository {

    val signInModel: MutableLiveData<DataState<SignIn>> = MutableLiveData(DataState.Idle())

    fun requestSignIn(email: String, password: String,deviceUniqId : String,token : String) {
        signInModel.postValue(DataState.Loading())
        Network.getRoutes().signIn(
            email = email,
            password = password,
            device_id = deviceUniqId,
            device_dev = Build.HARDWARE,
            fcm_token = token,
            latitude = "",
            longitude = ""
        ).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        signInModel.postValue(DataState.Success(Mapper.mapToSignIn(data)))
                    }else{
                        signInModel.postValue(DataState.Failed(Throwable(response.message())))
                    }
                }else{
                    signInModel.postValue(DataState.Failed(Throwable("Terjadi Kesalahan")))
                }

            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.printStackTrace()
                signInModel.postValue(DataState.Failed(Throwable(t)))
            }
        })
    }
}