package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.GetUserResponse
import com.hightech.ecommerce.data.response.UpdateProfileResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.callBackResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    val getUserResponse: MutableLiveData<DataState<GetUserResponse>> =
        MutableLiveData(DataState.Idle())

    val updateUser: MutableLiveData<DataState<UpdateProfileResponse>> =
        MutableLiveData(DataState.Idle())

    fun getUser() {
        Network.getRoutes().getUser().enqueue(object : Callback<GetUserResponse?> {
            override fun onResponse(
                call: Call<GetUserResponse?>,
                response: Response<GetUserResponse?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.data != null) {
                        getUserResponse.postValue(DataState.Success(data))
                    } else {
                        getUserResponse.postValue(
                            DataState.Failed(
                                Throwable(
                                    data?.message ?: "data tidak di temukan"
                                )
                            )
                        )
                    }
                } else {
                    val code = response.code()
                    val message = response.message()
                    if (code == 401) {
                        getUserResponse.postValue(DataState.Failed(Throwable("Unauthorized")))
                    } else {
                        getUserResponse.postValue(DataState.Failed(Throwable(message)))
                    }
                }
            }

            override fun onFailure(call: Call<GetUserResponse?>, t: Throwable) {
                t.printStackTrace()
                getUserResponse.postValue(DataState.Failed(t))
            }
        })
    }

    fun updateUser(name: String, phoneNumber: String, email: String, password: String) {
        updateUser.postValue(DataState.Loading())
        Network.getRoutes().updateUser(name, phoneNumber, email, password).enqueue(callBackResponse(
            result = {
                updateUser.postValue(it)
            }, error = {
                updateUser.postValue(it)
            })
        )
    }
}