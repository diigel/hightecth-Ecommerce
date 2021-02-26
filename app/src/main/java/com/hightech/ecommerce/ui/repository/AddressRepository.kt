package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.AddAddressResponse
import com.hightech.ecommerce.data.response.GetAddressResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.callBackResponse

class AddressRepository {

    val getAddress : MutableLiveData<DataState<GetAddressResponse>> = MutableLiveData(DataState.Idle())
    val addAddress : MutableLiveData<DataState<AddAddressResponse>> = MutableLiveData(DataState.Idle())

    fun getAddress() {
        getAddress.postValue(DataState.Loading())
        Network.getRoutes().getAddress().enqueue(callBackResponse(
            result = {
                getAddress.postValue(it)
            },error = {
                getAddress.postValue(it)
            }
        ))
    }

    fun addAddress(name : String,phoneNumber : String,email : String,address : String,provinceId : Int,cityId : Int,postalCode : Int){
        addAddress.postValue(DataState.Loading())
        Network.getRoutes().addAddress(name,phoneNumber,email,address,provinceId,cityId,postalCode).enqueue(callBackResponse(
            result = {
                addAddress.postValue(it)
            },error = {
             addAddress.postValue(it)
            }
        ))
    }
}