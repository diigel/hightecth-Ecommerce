package com.hightech.ecommerce.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.hightech.ecommerce.R
import com.hightech.ecommerce.databinding.FragmentHomeBinding
import com.hightech.ecommerce.ui.activity.CategoryActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding : FragmentHomeBinding? by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            imgMenu.setOnClickListener {
                startActivity(Intent(context,CategoryActivity::class.java))
            }
        }
    }


}