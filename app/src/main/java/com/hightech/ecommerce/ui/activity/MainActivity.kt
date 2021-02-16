package com.hightech.ecommerce.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.viewpager.widget.ViewPager
import com.hightech.ecommerce.ui.fragment.AccountFragment
import com.hightech.ecommerce.ui.fragment.HistoryFragment
import com.hightech.ecommerce.ui.fragment.HomeFragment
import com.hightech.ecommerce.R
import com.hightech.ecommerce.databinding.ActivityMainBinding
import com.hightech.ecommerce.base.SimplePagerAdapter
import com.hightech.ecommerce.utils.Broadcast
import kotlinx.coroutines.GlobalScope

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
        registerEvent()
    }

    private fun setupViewPager() {
        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val accountFragment = AccountFragment()

        val pagerAdapter = SimplePagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(
            homeFragment,
            historyFragment,
            accountFragment
        )

        binding.run {
            vpMain.adapter = pagerAdapter
            vpMain.offscreenPageLimit = pagerAdapter.count

            vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> navMain.selectedItemId = R.id.item_home
                        1 -> navMain.selectedItemId = R.id.item_history
                        2 -> navMain.selectedItemId = R.id.item_account
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}

            })

            navMain.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.item_home -> vpMain.currentItem = 0
                    R.id.item_history -> vpMain.currentItem = 1
                    R.id.item_account -> vpMain.currentItem = 2
                }
                true
            }
        }
    }

    private fun registerEvent() {
        Broadcast.with(GlobalScope).observer("sign_out") {
            startActivity(Intent(this, SignInActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }

    override fun onResume() {
        super.onResume()
        when (binding.vpMain.currentItem) {
            0 -> binding.navMain.selectedItemId = R.id.item_home
            1 -> binding.navMain.selectedItemId = R.id.item_history
            2 -> binding.navMain.selectedItemId = R.id.item_account
        }
    }

    override fun onBackPressed() {
        if (binding.vpMain.currentItem != 0) {
            binding.vpMain.setCurrentItem(0, false)
            binding.navMain.selectedItemId = R.id.home
        } else {
            //dialogExit()
        }
    }

}