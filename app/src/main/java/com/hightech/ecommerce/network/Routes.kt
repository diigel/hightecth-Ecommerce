package com.hightech.ecommerce.network

import com.hightech.ecommerce.data.response.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Routes {

    @FormUrlEncoded
    @POST("auth/login")
    fun signIn(
        @Field("email") email : String?,
        @Field("password") password : String?,
        @Field("device_id") device_id : String?,
        @Field("device_dev") device_dev : String?,
        @Field("fcm_token") fcm_token : String?,
        @Field("latitude") latitude : String?,
        @Field("longitude") longitude : String?
    ) : Call<SignInResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun signUp(
        @Field("name") name : String?,
        @Field("phone") phone : String?,
        @Field("email") email : String?,
        @Field("password") password : String?
    ) : Call<SignUpResponse>

    @POST("user")
    fun getUser() : Call<GetUserResponse>

    @POST("banner")
    fun getBanner() : Call<GetBannerResponse>

    @POST("product/category")
    fun getCategory() : Call<GetCategoryResponse>
}