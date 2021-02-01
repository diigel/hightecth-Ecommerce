package com.hightech.ecommerce.network

import com.hightech.ecommerce.utils.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("token", "${PreferencesManager.getAuthToken()}")
                .addHeader("deviceId", "${PreferencesManager.getAuthDeviceUniqId()}")
                .addHeader("deviceDev", "${PreferencesManager.getAuthDeviceDev()}")
                .build()
        )
    }
}