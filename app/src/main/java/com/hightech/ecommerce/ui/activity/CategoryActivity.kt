package com.hightech.ecommerce.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.hightech.ecommerce.R
import com.hightech.ecommerce.data.CategoryItem
import com.hightech.ecommerce.databinding.ActivityCategoryBinding
import com.hightech.ecommerce.ui.adapter.CategoryMenuAdapter
import com.hightech.ecommerce.base.SimplePagerAdapter2
import com.hightech.ecommerce.ui.fragment.FashionFragment

class CategoryActivity : AppCompatActivity() {

    private val binding: ActivityCategoryBinding by viewBinding()

    private val adapter by lazy {
        CategoryMenuAdapter(menuListener)
    }

    private val pagerAdapter  by lazy {
        SimplePagerAdapter2(this)
    }

    private val menuListener = object : CategoryMenuAdapter.MenuFocusListener {
        override fun onMenuItemFocused(position: Int) {
            when (position) {
                0 -> binding.vpCategory.currentItem = 0
                1 -> binding.vpCategory.currentItem = 1
                2 -> binding.vpCategory.currentItem = 2
                3 -> binding.vpCategory.currentItem = 3
                4 -> binding.vpCategory.currentItem = 4
                5 -> binding.vpCategory.currentItem = 5
                6 -> binding.vpCategory.currentItem = 6
                7 -> binding.vpCategory.currentItem = 7
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            val item0 = CategoryItem("Fashion", R.drawable.ic_menu_fashion)
            val item1 = CategoryItem("Makanan Dan Minuman", R.drawable.ic_menu_food)
            val item2 = CategoryItem("Elektronik", R.drawable.ic_menu_electronic)
            val item3 = CategoryItem("Olahraga", R.drawable.ic_menu_sport)
            val item4 = CategoryItem("Rumah Tangga", R.drawable.ic_menu_home_supplies)
            val item5 = CategoryItem("Dapur", R.drawable.ic_menu_kitchen)
            val item6 = CategoryItem("Kesehatan", R.drawable.ic_menu_health)
            val item7 = CategoryItem("Produk Lainnya", R.drawable.ic_menu_health)

            adapter.attachMenu(item0, item1, item2, item3, item4, item5, item6, item7)
            rvItemMenu.layoutManager = LinearLayoutManager(this@CategoryActivity)
            rvItemMenu.adapter = adapter

            pagerAdapter.addFragment(
                FashionFragment(),
                FashionFragment(),
                FashionFragment(),
                FashionFragment(),
                FashionFragment(),
                FashionFragment(),
                FashionFragment(),
                FashionFragment()
            )
            vpCategory.adapter = pagerAdapter
            vpCategory.offscreenPageLimit = 8
            vpCategory.isUserInputEnabled = false

            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        binding.run {
            if (vpCategory.currentItem == 0) {
                finish()
            } else {
                vpCategory.currentItem = vpCategory.currentItem - 1
            }
        }

    }

}