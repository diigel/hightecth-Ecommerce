package com.hightech.ecommerce.ui.activity

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hightech.ecommerce.base.DataStateListener
import com.hightech.ecommerce.base.bindToAlertDialog
import com.hightech.ecommerce.base.setOnListerner
import com.hightech.ecommerce.data.response.UpdateProfileResponse
import com.hightech.ecommerce.databinding.ActivityUpdateProfileBinding
import com.hightech.ecommerce.ui.viewmodel.UserViewModel
import com.hightech.ecommerce.utils.*

class UpdateProfileActivity : AppCompatActivity() {

    private val binding : ActivityUpdateProfileBinding by viewBinding()
    private val viewModel : UserViewModel by viewModels()
    private val validateButton = arrayListOf("","","","")
    private val loader by lazy {
        loaderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding.run {
           toolbar.setNavigationOnClickListener { finish() }
           setupSetInput(this)
           submitUpdateProfile(this)
       }
    }

    private fun setupSetInput(binding: ActivityUpdateProfileBinding)= binding.run {
        etName.textWacther { name->
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


        etConfirmPassword.textWacther { confirmPass ->
            when {
                !confirmPass.contains(PreferencesManager.getUserPassword() ?: "") -> {
                    validateButton[3] = ""
                    txtInputConfirmPassword.error = "Password anda salah"
                }
                confirmPass.isNotEmpty() -> {
                    validateButton[3] = confirmPass
                    txtInputConfirmPassword.error = null
                }
                else -> {
                    validateButton[3] = ""
                    txtInputConfirmPassword.error = null
                }
            }
            checkEnableButton()
        }

    }

    private fun checkEnableButton() {
        binding.btnSubmit.isEnabled = !validateButton.contains("")
    }

    private fun submitUpdateProfile(binding: ActivityUpdateProfileBinding) = binding.run {
        viewModel.updateProfile.observe(this@UpdateProfileActivity, Observer { state ->
            state.bindToAlertDialog(loader)
            state.setOnListerner(object : DataStateListener<UpdateProfileResponse> {
                override fun onLoading() {}

                override fun onIdle() {}

                override fun onSuccess(data: UpdateProfileResponse) {
                    if (data.status){
                        showDialogInfo(data.message,title = "Ganti Data Diri")
                    }else{
                        showDialogInfo(data.message,buttonText = "Kembali",title = "Ganti Data Diri",dialogResult = {
                            finish()
                        })
                    }
                }

                override fun onFailed(t: Throwable) {
                    showDialogInfo(t.message,title = "Ganti Data Diri")
                }

            })
        })
        btnSubmit.setOnClickListener {
            viewModel.requestUpadteProfile(
                name = validateButton[0],
                phoneNumber = validateButton[1],
                email = validateButton[2],
                password = validateButton[3]
            )
        }
    }
}