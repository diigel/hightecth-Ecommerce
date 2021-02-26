package com.hightech.ecommerce.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.hightech.ecommerce.R
import com.hightech.ecommerce.databinding.FragmentAccountBinding
import com.hightech.ecommerce.ui.activity.AddressActivity
import com.hightech.ecommerce.ui.activity.UpdateProfileActivity

class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding : FragmentAccountBinding? by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            txtAccountData.setOnClickListener {
                startActivity(Intent(context,UpdateProfileActivity::class.java))
            }
            txtAddress.setOnClickListener {
                startActivity(Intent(context,AddressActivity::class.java))
            }
        }
    }
}