package com.hightech.ecommerce.ui.repository

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.data.SignIn
import com.hightech.ecommerce.data.response.SignInResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import com.hightech.ecommerce.utils.PreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepository {

    val signInModel: MutableLiveData<SignIn> = MutableLiveData()

    fun requestSignIn(email: String, password: String,deviceUniqId : String,token : String) {
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
                val data = response.body()
                if (data != null) {
                    signInModel.postValue(Mapper.signIn(data))
                }else{
                    signInModel.postValue(null)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}