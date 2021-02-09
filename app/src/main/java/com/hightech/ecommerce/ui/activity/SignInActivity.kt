package com.hightech.ecommerce.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hightech.ecommerce.R
import com.hightech.ecommerce.databinding.ActivitySignInBinding
import com.hightech.ecommerce.ui.viewmodel.SigInViewModel
import com.hightech.ecommerce.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private val validateButton = mutableListOf("", "")
    private val viewmodel: SigInViewModel by viewModels()
    private val binding: ActivitySignInBinding by viewBinding()
    private val loader by lazy {
        loaderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkEnableButton()
        requestSignIn()
        validateTextInput()
        binding.run {
            txtSignup.setOnClickListener {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            }

            btnSigin.setOnClickListener {
                loader.show()
                getToken { token ->
                    viewmodel.requestSign(
                        validateButton[0],
                        validateButton[1],
                        getDeviceUniqueId(this@SignInActivity),
                        token
                    )
                }
            }

        }
    }

    private fun requestSignIn() {
        viewmodel.signIn.observe(this, Observer { response ->
            loader.dismiss()
            if (response.status) {
                saveAuth(
                    deviceUniqId = response.deviceId,
                    deviceDev = response.deviceDev,
                    token = response.token
                )
            } else {
                longToast(response.message)
            }
        })
    }

    private fun validateTextInput() {
        binding.run {
            etEmail.textWacther { email ->
                when {
                    !isEmailValid(email) -> {
                        txtInputEmail.error = "Format email salah"
                        validateButton[0] = ""
                    }
                    email.isNotEmpty() -> {
                        validateButton[0] = email
                        txtInputEmail.error = null
                    }
                    else -> {
                        validateButton[0] = ""
                        txtInputEmail.error = null
                    }
                }
                checkEnableButton()
            }
            etPassword.textWacther { password ->
                when {
                    password.isNotEmpty() -> {
                        validateButton[1] = password
                    }
                    else -> {
                        validateButton[1] = ""
                    }
                }
                checkEnableButton()
            }
        }
    }

    private fun checkEnableButton() {
        binding.btnSigin.isEnabled = !validateButton.contains("")
    }

    private fun saveAuth(deviceUniqId : String?,deviceDev: String?,token : String?){
        lifecycleScope.launch {
            delay(800)
            PreferencesManager.saveAuthDeviceDev(deviceDev ?:"")
            PreferencesManager.saveAuthDeviceUniqId(deviceUniqId?:"")
            PreferencesManager.saveAuthToken(token?:"")
            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        }

    }
}