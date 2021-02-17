package com.hightech.ecommerce.ui.repository

import androidx.lifecycle.MutableLiveData
import com.hightech.ecommerce.base.DataState
import com.hightech.ecommerce.data.CategoryItem
import com.hightech.ecommerce.data.response.GetCategoryResponse
import com.hightech.ecommerce.network.Network
import com.hightech.ecommerce.utils.Mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {

    val category : MutableLiveData<DataState<List<CategoryItem>>> = MutableLiveData(DataState.Idle())

    fun getCategory(){
        category.postValue(DataState.Loading())
        Network.getRoutes().getCategory().enqueue(object : Callback<GetCategoryResponse> {
            override fun onResponse(
                call: Call<GetCategoryResponse>,
                response: Response<GetCategoryResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.data
                    if (data != null){
                        category.postValue(DataState.Success(Mapper.mapToCategoryItem(data)))
                    }
                }else{
                    category.postValue(DataState.Failed(Throwable(response.message())))
                }
            }

            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
               category.postValue(DataState.Failed(t))
            }
        })
    }
}