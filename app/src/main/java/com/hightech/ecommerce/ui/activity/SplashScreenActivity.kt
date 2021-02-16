package com.hightech.ecommerce.ui.activity

import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hightech.ecommerce.base.DataStateListener
import com.hightech.ecommerce.base.bindToAlertDialog
import com.hightech.ecommerce.base.setOnListerner
import com.hightech.ecommerce.data.response.GetUserResponse
import com.hightech.ecommerce.databinding.ActivitySplashScreenBinding
import com.hightech.ecommerce.ui.viewmodel.UserViewModel
import com.hightech.ecommerce.utils.PreferencesManager
import com.hightech.ecommerce.utils.loaderDialog
import com.hightech.ecommerce.utils.logi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()
    private val binding: ActivitySplashScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            viewModel.getUser()
            getUser()
        }
    }

    private fun getUser() {
        viewModel.getUser.observe(this, Observer { user ->
            user.setOnListerner(object : DataStateListener<GetUserResponse> {
                override fun onLoading() {}
                override fun onIdle() {}
                override fun onSuccess(data: GetUserResponse) {
                    if (data.data != null) {
                        lifecycleScope.launch {
                            delay(4000)
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                    } else {
                        isFirstLogin()
                    }
                }

                override fun onFailed(t: Throwable) {
                    if (t.message == "Unauthorized") {
                        isFirstLogin()
                    }
                    logi(t.localizedMessage ?: "kkkkkk")
                    t.printStackTrace()
                }
            })
        })
    }

    private fun isFirstLogin() {
        lifecycleScope.launch {
            delay(4000)
            PreferencesManager.getIsFirstLaunchAsync().collect {
                if (it) {
                    startActivity(
                        Intent(
                            this@SplashScreenActivity,
                            BoardingPageActivity::class.java
                        )
                    )
                } else {
                    startActivity(
                        Intent(
                            this@SplashScreenActivity,
                            SignInActivity::class.java
                        )
                    )
                }
            }
        }
    }
}