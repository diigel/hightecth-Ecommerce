package com.hightech.ecommerce.utils

import com.hightech.ecommerce.base.DataState
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T : Any> callBackResponse(
    result: ((DataState.Success<T>) -> Unit)? = null,
    error: ((DataState.Failed<T>) -> Unit)? = null
) = object : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null){
                result?.invoke(DataState.Success(data))
            }else{
                error?.invoke(DataState.Failed(Throwable("Maaf terjadi kesalahan")))
            }
        }else{
            val code = response.code()
            val message = response.message()
            if (code == 401) {
                Broadcast.with(GlobalScope).post("sign_out")
                error?.invoke(DataState.Failed(Throwable("Unauthorized")))
            } else {
                error?.invoke(DataState.Failed(Throwable(message)))
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
       t.printStackTrace()
        error?.invoke(DataState.Failed(t))
    }

}