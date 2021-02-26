package com.hightech.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hightech.ecommerce.data.response.GetAddressResponse
import com.hightech.ecommerce.databinding.ItemListAddressBinding
import com.hightech.ecommerce.utils.removeDuplicatesItem

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private val dataList: MutableList<GetAddressResponse.Data> = mutableListOf()

    fun addList(data: List<GetAddressResponse.Data>) {
        dataList.clear()
        dataList.addAll(data)
        dataList.removeDuplicatesItem()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemListAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class AddressViewHolder(private val binding: ItemListAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GetAddressResponse.Data) = binding.run {
            txtTitleAddress.text = "Kantor"
            txtReceiverName.text = data.name
            txtReceiverPhone.text = data.phone
            txtReceiverAddress.text = "${data.address}, ${data.province_name}, ${data.city_name}, ${data.postal_code}"

            btnUpdateAddress.setOnClickListener {

            }
        }
    }
}