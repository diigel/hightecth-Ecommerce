package com.hightech.ecommerce.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.base.DataStateListener
import com.hightech.ecommerce.base.bindToAlertDialog
import com.hightech.ecommerce.base.setOnListerner
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.databinding.ActivitySignUpBinding
import com.hightech.ecommerce.ui.viewmodel.SignUpViewModel
import com.hightech.ecommerce.utils.*

class SignUpActivity : AppCompatActivity() {

    private val viewmodel: SignUpViewModel by viewModels()
    private val binding: ActivitySignUpBinding by viewBinding()
    private val validateButton = mutableListOf("", "", "", "", "")
    private val loader by lazy {
        loaderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkEnableButton()
        requestSignUp()
        validateTextInput()
        binding.run {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                finish()
            }

            btnSigup.setOnClickListener {
                loader.show()
                viewmodel.requestSignUp(
                    validateButton[0],
                    validateButton[1],
                    validateButton[2],
                    validateButton[3]
                )
            }
        }
    }

    private fun requestSignUp() {
        viewmodel.signUpState.observe(this, Observer { state ->
            state.bindToAlertDialog(loader)
            state.setOnListerner(object : DataStateListener<SignUp> {
                override fun onLoading() {}
                override fun onIdle() {}
                override fun onFailed(t: Throwable) {}
                override fun onSuccess(data: SignUp) {
                    if (data.status) {
                        showDialogInfo(
                            data.message,
                            buttonText = "Kembali",
                            title = "Registrasi",
                            dialogResult = {
                                finish()
                            })
                    } else {
                        longToast(data.message)
                    }
                }
            })
        })
    }

    private fun validateTextInput() {
        binding.run {
            etName.textWacther { name ->
                when {
                    name.length < 6 -> {
                        validateButton[0] = ""
                        txtInputName.error = "Nama minimal 6 karakter"
                    }
                    name.isNotEmpty() -> {
                        validateButton[0] = name
                        txtInputName.error = null
                    }
                    else -> {
                        validateButton[0] = ""
                        txtInputName.error = null
                    }
                }
                checkEnableButton()
            }

            etPhoneNumber.textWacther(true) { phoneNumber ->
                when {
                    phoneNumber.length < 10 -> {
                        validateButton[1] = ""
                        txtInputPhoneNumber.error = "Minimal nomer handphoen 10 karakter"
                    }
                    phoneNumber.length > 20 -> {
                        validateButton[1] = ""
                        txtInputPhoneNumber.error = "Maksimal nomer handphoen 20 karakter"
                    }
                    phoneNumber.isNotEmpty() -> {
                        validateButton[1] = phoneNumber
                        txtInputPhoneNumber.error = null
                    }

                    else -> {
                        validateButton[1] = ""
                        txtInputPhoneNumber.error = null
                    }
                }
                checkEnableButton()
            }

            etEmail.textWacther { email ->
                when {
                    !isEmailValid(email) -> {
                        txtInputEmail.error = "Format email salah"
                        validateButton[2] = ""
                    }
                    email.isNotEmpty() -> {
                        validateButton[2] = email
                        txtInputEmail.error = null
                    }
                    else -> {
                        validateButton[2] = ""
                        txtInputEmail.error = null
                    }
                }
                checkEnableButton()
            }

            etPassword.textWacther { password ->
                when {
                    password.length < 6 -> {
                        validateButton[3] = ""
                        txtInputPassword.error = "Password minimal 6 karakter"
                    }
                    password.isNotEmpty() -> {
                        validateButton[3] = password
                        txtInputPassword.error = null
                    }
                }
                checkEnableButton()
            }

            etConfirmPassword.textWacther { confirmPass ->
                when {
                    !confirmPass.contains(validateButton[3]) -> {
                        validateButton[4] = ""
                        txtInputConfirmPassword.error = "Password tidak sama"
                    }
                    confirmPass.isNotEmpty() -> {
                        validateButton[4] = confirmPass
                        txtInputConfirmPassword.error = null
                    }
                    else -> {
                        validateButton[4] = ""
                        txtInputConfirmPassword.error = null
                    }
                }
                checkEnableButton()
            }
        }
    }

    private fun checkEnableButton() {
        binding.btnSigup.isEnabled = !validateButton.contains("")
    }
}