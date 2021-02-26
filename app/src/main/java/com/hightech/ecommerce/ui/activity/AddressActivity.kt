package com.hightech.ecommerce.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hightech.ecommerce.base.DataStateListener
import com.hightech.ecommerce.base.bindToAlertDialog
import com.hightech.ecommerce.base.setOnListerner
import com.hightech.ecommerce.data.response.GetAddressResponse
import com.hightech.ecommerce.databinding.ActivityAddressBinding
import com.hightech.ecommerce.ui.adapter.AddressAdapter
import com.hightech.ecommerce.ui.viewmodel.AddressViewModel
import com.hightech.ecommerce.utils.loaderDialog
import com.hightech.ecommerce.utils.showDialogInfo

class AddressActivity : AppCompatActivity() {

    private val binding : ActivityAddressBinding by viewBinding()
    private val viewModel : AddressViewModel by viewModels()
    private val loader by lazy {
        loaderDialog()
    }
    private val adapter by lazy {
        AddressAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            toolbar.setNavigationOnClickListener { finish() }
            imgAddAddress.setOnClickListener { startActivity(Intent(this@AddressActivity,AddAddressActivity::class.java)) }
            rvAddress.layoutManager = LinearLayoutManager(this@AddressActivity)
            rvAddress.adapter = adapter
            getAddress()
        }
    }

    private fun getAddress(){
        viewModel.requestGetAddrest()
        viewModel.getAddress.observe(this, Observer { state ->
            state.bindToAlertDialog(loader)
            state.setOnListerner(object : DataStateListener<GetAddressResponse> {
                override fun onLoading() {}
                override fun onIdle() {}

                override fun onSuccess(data: GetAddressResponse) {
                    if (data.status){
                        adapter.addList(data.data)
                    }else{
                        showDialogInfo(data.message)
                    }
                }

                override fun onFailed(t: Throwable) {
                    t.printStackTrace()
                }
            })
        })
    }
}