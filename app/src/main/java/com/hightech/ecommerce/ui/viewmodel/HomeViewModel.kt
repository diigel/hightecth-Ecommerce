package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.BannerItem
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.ui.repository.HomeRepository

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()
    val getBanner : LiveData<DataState<List<BannerItem>>> = repository.getBanner

    fun getBanner (){
        repository.getBanner()
    }
}