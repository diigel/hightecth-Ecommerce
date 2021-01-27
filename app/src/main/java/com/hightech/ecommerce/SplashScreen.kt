package com.hightech.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hightech.ecommerce.auth.SignInActivity
import com.hightech.ecommerce.boarding_page.BoardingPageActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(4000)

            PreferencesManager.getIsFirstLaunchAsync().collect {
                if (it) {
                    startActivity(Intent(this@SplashScreen, BoardingPageActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreen, SignInActivity::class.java))
                }
            }
        }
    }
}