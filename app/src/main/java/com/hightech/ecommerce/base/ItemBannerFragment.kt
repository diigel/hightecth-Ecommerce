package com.hightech.ecommerce.base

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.hightech.ecommerce.R
import com.hightech.ecommerce.data.response.GetBannerResponse
import com.hightech.ecommerce.databinding.SliderBannerBinding
import com.hightech.ecommerce.utils.loadImage

class ItemBannerFragment : Fragment(R.layout.slider_banner) {

    private var dataBanner: GetBannerResponse.DataBanner? = null
    private val binding : SliderBannerBinding by viewBinding()

    companion object {
        fun newInstance(dataBanner: GetBannerResponse.DataBanner): ItemBannerFragment {
            val bundle = Bundle()
            bundle.putParcelable("data", dataBanner)
            val itemBanner = ItemBannerFragment()
            itemBanner.arguments = bundle
            return itemBanner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBanner = arguments?.getParcelable("data")
        binding.imageView.loadImage(dataBanner?.image)
    }
}