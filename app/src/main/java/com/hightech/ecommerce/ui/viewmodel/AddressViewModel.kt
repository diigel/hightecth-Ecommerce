package com.hightech.ecommerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.AddAddressResponse
import com.hightech.ecommerce.data.response.GetAddressResponse
import com.hightech.ecommerce.ui.repository.AddressRepository

class AddressViewModel : ViewModel() {

    private val repository = AddressRepository()

    val getAddress : LiveData<DataState<GetAddressResponse>> = repository.getAddress
    val addAddress : LiveData<DataState<AddAddressResponse>> = repository.addAddress

    fun requestGetAddrest(){
        repository.getAddress()
    }

    fun requestAddAddress(name : String,phoneNumber : String,email : String,address: String,provinceId :Int,cityId : Int,postalCode : Int){
        repository.addAddress(name, phoneNumber, email, address, provinceId, cityId, postalCode)
    }
}