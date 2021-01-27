package com.hightech.ecommerce

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.hightech.ecommerce.databinding.FragmentAccountBinding

class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding : FragmentAccountBinding? by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {

        }
    }
}