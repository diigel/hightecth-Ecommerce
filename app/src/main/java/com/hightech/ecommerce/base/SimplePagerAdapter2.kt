package com.hightech.ecommerce.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class SimplePagerAdapter2(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity)  {

    private val fragmentItems: MutableList<Fragment> = mutableListOf()
    override fun getItemCount(): Int = fragmentItems.size

    override fun createFragment(position: Int): Fragment = fragmentItems[position]

    fun addFragment(vararg fragment: Fragment) {
        fragmentItems.addAll(fragment)
    }
}