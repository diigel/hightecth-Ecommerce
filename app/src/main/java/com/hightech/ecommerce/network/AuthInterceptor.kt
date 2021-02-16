package com.hightech.ecommerce.network

import com.hightech.ecommerce.utils.Broadcast
import kotlinx.coroutines.GlobalScope
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            Broadcast.with(GlobalScope).post("sign_out")
            return response
        }

        return response
    }
}