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
import com.hightech.ecommerce.data.SignIn
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
            response.bindToAlertDialog(loader)
            response.setOnListerner(object : DataStateListener<SignIn> {
                override fun onLoading() {}
                override fun onIdle() {}
                override fun onSuccess(data: SignIn) {
                    if (data.status){
                        saveAuth(
                            deviceUniqId = data.deviceId,
                            deviceDev = data.deviceDev,
                            token = data.token
                        )
                    }else{
                        showDialogInfo(data.message)
                    }
                }
                override fun onFailed(t: Throwable) {
                    t.printStackTrace()
                }
            })
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