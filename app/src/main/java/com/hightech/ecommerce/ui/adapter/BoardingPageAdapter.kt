package com.hightech.ecommerce.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.hightech.ecommerce.R
import com.hightech.ecommerce.utils.layoutInflater
import com.hightech.ecommerce.utils.loadImage

class BoardingPageAdapter(private val pageIs: Array<Int>) : PagerAdapter() {

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = container.layoutInflater(R.layout.item_boarding_page)
        val img = view.findViewById<ImageView>(R.id.img_tumbnail)
        val title = view.findViewById<TextView>(R.id.txt_title)
        val desc = view.findViewById<TextView>(R.id.txt_desc)
        when (position) {
            0 -> {
                img.loadImage(drawableRes = R.drawable.boarding_page1)
                title.text = "Jelajahi"
                desc.text = "Cari, temukan, dan beli produk favorit mu "
            }
            1 -> {
                img.loadImage(drawableRes = R.drawable.boarding_page2)
                title.text = "Pembayaran Mudah"
                desc.text = "Gunakan pembayaran sesuai dengan pilihanmu"
            }
            2 -> {
                img.loadImage(drawableRes = R.drawable.boarding_page3)
                title.text = "Pengalaman Berbelanja"
                desc.text = "Nikmati kenyaman berbelanja saat menjelajahi toko favoritmu"
            }
        }
        container.addView(view)
        return view
    }

    override fun getCount(): Int = pageIs.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}