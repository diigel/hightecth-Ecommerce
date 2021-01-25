package com.hightech.ecommerce

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

open class EcommerceApplication : Application() {

    companion object {
        lateinit var instance: EcommerceApplication
        fun getApplicationContext() = instance.applicationContext
    }

    override fun getApplicationContext(): Context {
        instance = this
        return super.getApplicationContext()
    }

    override fun onCreate() {
        super.onCreate()

        // block dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}