package com.hightech.ecommerce.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hightech.ecommerce.R
import com.hightech.ecommerce.base.*
import com.hightech.ecommerce.data.response.GetUserResponse
import com.hightech.ecommerce.databinding.FragmentHomeBinding
import com.hightech.ecommerce.ui.activity.CategoryActivity
import com.hightech.ecommerce.ui.viewmodel.HomeViewModel
import com.hightech.ecommerce.ui.viewmodel.UserViewModel
import com.hightech.ecommerce.utils.loaderDialog
import com.hightech.ecommerce.utils.logi
import kotlinx.android.synthetic.main.banner_layout.*
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding : FragmentHomeBinding? by viewBinding()
    private val viewModel : HomeViewModel by viewModels()
    private val viewModelUser : UserViewModel by viewModels()
    private val loader by lazy {
        context?.loaderDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            imgMenu.setOnClickListener {
                startActivity(Intent(context,CategoryActivity::class.java))
            }
            setupSlider()
            saveAuth()
        }
    }

    private fun setupSlider(){
        viewModel.getBanner()
        viewModel.getBanner.observe(viewLifecycleOwner, Observer { state ->
            state.bindToAlertDialog(loader)
            state.setOnListerner(object : DataStateListener<List<BannerItem>> {
                override fun onLoading() {}
                override fun onIdle() {}
                override fun onFailed(t: Throwable) {
                 t.printStackTrace()
                }

                override fun onSuccess(data: List<BannerItem>) {
                    val banner = BaseImageSlider(childFragmentManager)
                    banner.setScaleType(ImageView.ScaleType.FIT_XY)
                    banner.withCardView(false)
                    banner.attachLayout(banner_viewpager)
                        .withItems(data)

                    banner.start()
                }
            })
        })
    }

    private fun saveAuth(){
        viewModelUser.getUser()
        viewModelUser.getUser.observe(viewLifecycleOwner, Observer { state ->
            state.bindToAlertDialog(loader)
            state.setOnListerner(object : DataStateListener<GetUserResponse> {
                override fun onLoading() {}
                override fun onIdle() {}

                override fun onSuccess(data: GetUserResponse) {
                  if (data.status){
                      val dataAuth = data.data
                      lifecycleScope.launch {
//                          PreferencesManager.saveAuthDeviceDev(dataAuth. ?:"")
//                          PreferencesManager.saveAuthDeviceUniqId(deviceUniqId?:"")
//                          PreferencesManager.saveAuthToken(token?:"")
                      }
                  }else{
                      logi(data.message)
                  }
                }

                override fun onFailed(t: Throwable) {
                   t.printStackTrace()
                }

            })
        })
    }


}