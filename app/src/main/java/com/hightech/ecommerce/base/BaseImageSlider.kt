package com.hightech.ecommerce.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.hightech.ecommerce.R
import com.hightech.ecommerce.utils.Broadcast
import com.hightech.ecommerce.utils.loadImage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_layout_banner.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BaseImageSlider(fragmentManager: FragmentManager) {

    interface ItemClickListener {
        fun onItemSelected(position: Int)
    }

    private val bannerPagerAdapter = BannerPagerAdapter(fragmentManager)
    private lateinit var viewPager: ViewPager
    private lateinit var bannerItem: List<BannerItem>
    private val liveColorRgb: MutableLiveData<String> = MutableLiveData()

    private val fragments: MutableList<BannerFragment> = mutableListOf()
    private var scaleType: ImageView.ScaleType? = ImageView.ScaleType.CENTER
    private var itemClickListener: ItemClickListener? = null
    private var zoomable = false
    private var withCardView = true

    fun setScaleType(scaleType: ImageView.ScaleType) {
        this.scaleType = scaleType
    }

    fun setZoomable(zoomable: Boolean) {
        this.zoomable = zoomable
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun withCardView(value: Boolean) {
        withCardView = value
    }

    fun attachLayout(viewPager: ViewPager): BaseImageSlider {
        viewPager.adapter = bannerPagerAdapter
        this.viewPager = viewPager

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val colorRgb = fragments[position].getColor()
                liveColorRgb.postValue(colorRgb)
            }

        })

        return this
    }

    fun withItems(bannerItem: List<BannerItem>) {
        this.bannerItem = bannerItem

        bannerItem.forEachIndexed { index, item ->
            item.position = index
            val fragment = BannerFragment.newInstance(item)
            bannerPagerAdapter.addFragment(fragment)
            bannerPagerAdapter.notifyDataSetChanged()
            fragment.setScaleType(scaleType)
            fragment.zoomable(zoomable)
            fragment.setColor(item.color)
            fragment.withCardView(withCardView)
            itemClickListener?.let { click -> fragment.itemClick(click) }
            fragments.add(fragment)

            GlobalScope.launch {
                delay(500)
                val colorRgb = fragments[0].getColor() ?: "#0160ee"
                liveColorRgb.postValue(colorRgb)
            }

        }
    }

    fun start() {
        Broadcast.with(GlobalScope).observer("banner_timer") {
            if (viewPager.currentItem < bannerPagerAdapter.count - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                viewPager.currentItem = 0
            }
        }
    }

    fun scrollToItem(position: Int, animation: Boolean = true) {
        viewPager.setCurrentItem(position, animation)
    }

    fun getColorNow() = liveColorRgb.value

    fun listenLiveColor() = liveColorRgb

}

class BannerFragment : Fragment() {
    private var bannerItem: BannerItem? = null
    private var color: String? = "#0160ee"
    private var scaleType: ImageView.ScaleType? = null
    private var itemClickListener: BaseImageSlider.ItemClickListener? = null
    private var zoomable = false
    private var withCardView = false

    companion object {
        fun newInstance(bannerItem: BannerItem): BannerFragment {
            val bundle = Bundle()
            bundle.putParcelable("data", bannerItem)
            val itemBanner = BannerFragment()
            itemBanner.arguments = bundle
            return itemBanner
        }
    }

    fun setColor(color: String?) {
        this.color = color
    }

    fun getColor(): String? {
        return this.color
    }

    fun itemClick(itemClickListener: BaseImageSlider.ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setScaleType(scaleType: ImageView.ScaleType? = null) {
        this.scaleType = scaleType
    }

    fun zoomable(zoomable: Boolean) {
        this.zoomable = zoomable
    }

    fun withCardView(value: Boolean) {
        withCardView = value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bannerItem = arguments?.getParcelable("data")
        return inflater.inflate(R.layout.item_layout_banner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!zoomable) {
            image_view.loadImage(bannerItem?.url, resizeTo = 820)
            image_view.isVisible = true
            image_view_zoom.isGone = true

            image_view_card.loadImage(bannerItem?.url, resizeTo = 820)
            image_view_card.isVisible = true
            image_view_zoom_card.isGone = true
        } else {
            image_view_zoom.loadImage(bannerItem?.url, resizeTo = 820)
            image_view.isGone = true
            image_view_zoom.isVisible = true

            image_view_zoom_card.loadImage(bannerItem?.url, resizeTo = 820)
            image_view_card.isGone = true
            image_view_zoom_card.isVisible = true
        }

        if (withCardView) {
            card_container.isVisible = true
            without_card_container.isGone = true
        } else {
            card_container.isGone = true
            without_card_container.isVisible = true
        }

        image_view.setOnClickListener {
            itemClickListener?.onItemSelected(bannerItem?.position ?: 0)
        }

        image_view_card.setOnClickListener {
            itemClickListener?.onItemSelected(bannerItem?.position ?: 0)
        }

        if (scaleType != null) {
            image_view_card.scaleType = scaleType
            image_view.scaleType = scaleType
            image_view_zoom.scaleType = scaleType
            image_view_zoom_card.scaleType = scaleType
        }
    }
}

class BannerPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentItems: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragmentItems[position]

    override fun getCount(): Int = fragmentItems.size

    fun addFragment(vararg fragment: Fragment) {
        fragmentItems.addAll(fragment)
    }
}

@Parcelize
class BannerItem(
    val url: String?,
    val identifier: Int? = null,
    val tag: String? = null,
    var position: Int? = null,
    var color: String? = "#0160ee"
) : Parcelable


class ViewPagerBanner : ViewPager {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }
}