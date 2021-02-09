package com.hightech.ecommerce.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hightech.ecommerce.data.RootHome
import com.hightech.ecommerce.utils.removeDuplicatesItem

class RootHomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList : MutableList<RootHome> = mutableListOf()
    lateinit var parent : ViewGroup


    fun addList(data : List<RootHome>){
        dataList.addAll(data)
        dataList.sortBy { it.type }
        dataList.removeDuplicatesItem()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}