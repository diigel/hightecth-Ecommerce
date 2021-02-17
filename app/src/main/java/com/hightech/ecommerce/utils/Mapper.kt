package com.hightech.ecommerce.utils

import com.hightech.ecommerce.R
import com.hightech.ecommerce.base.BannerItem
import com.hightech.ecommerce.data.CategoryItem
import com.hightech.ecommerce.data.SignIn
import com.hightech.ecommerce.data.SignUp
import com.hightech.ecommerce.data.response.GetBannerResponse
import com.hightech.ecommerce.data.response.GetCategoryResponse
import com.hightech.ecommerce.data.response.SignInResponse
import com.hightech.ecommerce.data.response.SignUpResponse

object Mapper {

    fun mapToSignIn(response : SignInResponse) : SignIn {
        return SignIn(
            userId = response.data?.user_id,
            token = response.data?.token,
            deviceId = response.data?.device_id,
            deviceDev = response.data?.device_dev,
            latitude = response.data?.latitude,
            longitude = response.data?.longitude,
            userName = response.data?.user?.name,
            userPhone = response.data?.user?.phone,
            userEmail = response.data?.user?.email,
            message = response.message,
            status = response.status
        )
    }

    fun mapToSignUp(response: SignUpResponse) : SignUp {
        return SignUp(
            status = response.status,
            message = response.message,
            name = response.data?.name,
            phone = response.data?.phone,
            email = response.data?.email
        )
    }

    fun mapToBannerItem(response: List<GetBannerResponse.DataBanner>) : List<BannerItem> {
        val bannerItem : MutableList<BannerItem> = mutableListOf()
        response.forEach {
            bannerItem.add(BannerItem(url = it.image))
        }

        return bannerItem
    }

    fun mapToCategoryItem(response: List<GetCategoryResponse.Data>) : List<CategoryItem>{
        val categoryItem : MutableList<CategoryItem> = mutableListOf()

        response.forEach { data ->
            categoryItem.add(CategoryItem(title = data.name,icon = R.drawable.ic_menu_fashion))
        }
        logi("cat -> $categoryItem")
        return categoryItem
    }
}