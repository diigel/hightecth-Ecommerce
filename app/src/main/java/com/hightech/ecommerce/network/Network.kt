package com.hightech.ecommerce.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hightech.ecommerce.BuildConfig
import com.hightech.ecommerce.EcommerceApplication
import com.hightech.ecommerce.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        /**
         * @BODY if you need show all response
         * @BASIC only show end_point response
         * @NONE nothing
         * */
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    private fun provideOkHttpClient(header: Boolean? = null ?: true): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .retryOnConnectionFailure(false)
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(AuthInterceptor())

        if (header == true) builder.addInterceptor(HeaderInterceptor())

        return builder.build()
    }

    private fun provideRetrofit(header: Boolean? = null): Retrofit {

        /**
         * setFieldNamingPolicy()
         * for convert lowercase with underscores
         * json:`user_name`, you can use `userName` as variable
         */
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .setPrettyPrinting()
            .create()

        val builder = Retrofit.Builder()
            .baseUrl(EcommerceApplication.getApplicationContext().getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOkHttpClient(header))

        return builder.build()
    }

    fun getRoutes(header: Boolean? = true): Routes =
        provideRetrofit(header).create(Routes::class.java)

}