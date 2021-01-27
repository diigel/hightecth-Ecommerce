package com.hightech.ecommerce.boarding_page

import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.hightech.ecommerce.MainActivity
import com.hightech.ecommerce.PreferencesManager
import com.hightech.ecommerce.databinding.ActivityBoardingPageBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BoardingPageActivity : AppCompatActivity() {
    private val binding: ActivityBoardingPageBinding by viewBinding()

    private val pageIs = arrayOf(0, 1, 2,3)
    private val adapter by lazy {
        BoardingPageAdapter(pageIs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            vpBoardingPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) { if (position >2) {
                        lifecycleScope.launch {
                            val current = vpBoardingPage.currentItem + 1
                            if (current < pageIs.size){
                                vpBoardingPage.currentItem = current
                            }else{
                                PreferencesManager.saveIsFirstLaunch(isFirstLaunch = false)
                                startActivity(Intent(this@BoardingPageActivity, MainActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    })
                                finish()
                            }
                        }
                    }
                }
                override fun onPageSelected(position: Int) { }
            })
            vpBoardingPage.adapter = adapter
        }

    }


}