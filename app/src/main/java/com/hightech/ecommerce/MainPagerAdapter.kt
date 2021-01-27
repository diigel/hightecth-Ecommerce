package com.hightech.ecommerce

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentItems: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragmentItems[position]

    override fun getCount(): Int = fragmentItems.size

    fun addFragment(vararg fragment: Fragment) {
        fragmentItems.addAll(fragment)
    }
}
