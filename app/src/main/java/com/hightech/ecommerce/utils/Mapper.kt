package com.hightech.ecommerce.utils

import com.hightech.ecommerce.data.SignIn
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.data.response.SignInResponse
import com.hightech.ecommerce.data.response.SignUpResponse

object Mapper {

    fun signIn(response : SignInResponse) : SignIn {
        return SignIn(
            userId = response.data?.user_id,
            token = response.data?.token,
            deviceId = response.data?.device_id,
            deviceDev = response.data?.device_dev,
            latitude = response.data?.latitude,
            longitude = response.data?.longitude,
            userName = response.data?.user?.name,
            userPhone = response.data?.user?.phone,
            userEmail = response.data?.user?.email,
            message = response.message,
            status = response.status
        )
    }

    fun signUp(response: SignUpResponse) : SignUp {
        return SignUp(
            status = response.status,
            message = response.message,
            name = response.data?.name,
            phone = response.data?.phone,
            email = response.data?.email
        )
    }
}