package com.hightech.ecommerce.ui.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hightech.ecommerce.R
import com.hightech.ecommerce.data.CategoryItem
import com.hightech.ecommerce.utils.layoutInflater
import com.hightech.ecommerce.utils.loadImage
import kotlinx.android.synthetic.main.item_list_category.view.*

class CategoryMenuAdapter(private val focusListener: MenuFocusListener) : RecyclerView.Adapter<CategoryMenuAdapter.CategoryMenuViewHolder>() {

    private val dataItem : MutableList<CategoryItem> = mutableListOf()
    private var globalPosition = 0

    fun attachMenu(vararg item: CategoryItem){
        dataItem.addAll(item)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMenuViewHolder {
        return CategoryMenuViewHolder(parent.layoutInflater(R.layout.item_list_category))
    }

    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int) {
        holder.bind(dataItem[position],position,focusListener)
    }

    override fun getItemCount(): Int {
        return dataItem.size
    }

    inner class CategoryMenuViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind (data : CategoryItem,position: Int,focusListener: MenuFocusListener) = itemView.run {
            txt_title_active.text = data.title
            txt_title_inactive.text = data.title

            img_active.loadImage(drawableRes = data.icon)
            img_inactive.loadImage(drawableRes = data.icon)

            setOnClickListener {
                globalPosition = position
                notifyDataSetChanged()
            }

            if (globalPosition == position) {
                focusListener.onMenuItemFocused(position)
                lin_active.visibility = View.VISIBLE
                lin_inactive.visibility = View.GONE
                setBackgroundColor(Color.WHITE)
            } else {
                lin_active.visibility = View.GONE
                lin_inactive.visibility = View.VISIBLE
                setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    interface MenuFocusListener {
        fun onMenuItemFocused(position: Int)
    }
}