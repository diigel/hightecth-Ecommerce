package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.BannerItem
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.response.GetBannerResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    val getBanner : MutableLiveData<DataState<List<BannerItem>>> = MutableLiveData(DataState.Idle())

    fun getBanner(){
        getBanner.postValue(DataState.Loading())
        Network.getRoutes().getBanner().enqueue(object : Callback<GetBannerResponse> {
            override fun onResponse(
                call: Call<GetBannerResponse>,
                response: Response<GetBannerResponse>
            ) {
                if (response.isSuccessful){
                   val dataBanner = response.body()?.data
                    if (dataBanner != null){
                        getBanner.postValue(DataState.Success(Mapper.mapToBannerItem(dataBanner)))
                    }else{
                        getBanner.postValue(DataState.Failed(Throwable(response.message())))
                    }
                }else{
                    getBanner.postValue(DataState.Failed(Throwable("Terjadi Kesalahan")))
                }
            }

            override fun onFailure(call: Call<GetBannerResponse>, t: Throwable) {
                t.printStackTrace()
                getBanner.postValue(DataState.Failed(t))
            }

        })
    }
}