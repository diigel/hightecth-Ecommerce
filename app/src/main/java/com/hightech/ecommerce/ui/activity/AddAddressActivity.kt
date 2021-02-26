package com.hightech.ecommerce.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import com.hightech.ecommerce.databinding.ActivityAddAddressBinding
import com.hightech.ecommerce.ui.viewmodel.AddressViewModel
import com.hightech.ecommerce.utils.loaderDialog

class AddAddressActivity : AppCompatActivity() {

    private val binding : ActivityAddAddressBinding by viewBinding()
    private val viewModel : AddressViewModel by viewModels()
    private val loader by lazy {
        loaderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding.run {
           toolbar.setNavigationOnClickListener { finish() }
       }
    }
}