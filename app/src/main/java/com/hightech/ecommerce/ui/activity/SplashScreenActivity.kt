package com.hightech.ecommerce.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hightech.ecommerce.R
import com.hightech.ecommerce.utils.PreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(4000)

            PreferencesManager.getIsFirstLaunchAsync().collect {
                if (it) {
                    startActivity(Intent(this@SplashScreenActivity, BoardingPageActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity, SignInActivity::class.java))
                }
            }
        }
    }
}