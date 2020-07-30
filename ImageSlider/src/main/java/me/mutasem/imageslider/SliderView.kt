package me.mutasem.imageslider

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class SliderView : RelativeLayout {
    val TAG = "SliderView"
    private lateinit var tabLayout: TabLayout
    lateinit var slideAdapter: SlideAdapter
    lateinit var viewPager2: ViewPager2
    private var autoSlide = false
    private var slideDuration = 1000
    var captionTextColor: Int? = null
    var captionTextSize: Float? = null
    var captionBgColor: Int? = null
    val autoSlideRunnable = object : Runnable {
        override fun run() {
            val nextPage =
                if (viewPager2.currentItem == (viewPager2.adapter?.itemCount?.minus(1))) 0
                else
                    viewPager2.currentItem + 1
            viewPager2.setCurrentItem(nextPage, false)
            postDelayed(this, slideDuration.toLong())
        }


    }

    constructor(context: Context?) : super(context) {
        viewInit(context, null, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        viewInit(context, attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        viewInit(context, attrs, defStyleAttr, 0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        viewInit(context, attrs, defStyleAttr, defStyleRes)
    }

    fun viewInit(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        View.inflate(context, R.layout.slider_layout, this)
        slideAdapter = SlideAdapter(
            arrayListOf()
        )
        viewPager2 = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tabs)
        viewPager2.adapter = slideAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            viewPager2.setCurrentItem(tab.position, false)
        }.attach()
        if (attrs != null) {
            val a = context?.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.SliderView,
                defStyleAttr,
                defStyleRes
            )
            try {
                if (a != null) {
                    autoSlide = a.getBoolean(R.styleable.SliderView_autoSlide, false)
                    slideDuration = a.getInt(R.styleable.SliderView_slideDuration, 1000)
                    captionTextColor = a.getColor(
                        R.styleable.SliderView_captionTextColor,
                        ContextCompat.getColor(context, android.R.color.black)
                    )
                    captionTextSize = a.getDimension(R.styleable.SliderView_captionTextSize, 0F)
                    captionBgColor = a.getColor(
                        R.styleable.SliderView_captionBgColor,
                        ContextCompat.getColor(context, android.R.color.transparent)
                    )
                    slideAdapter.setCaptionAttrs(
                        captionTextColor!!, captionBgColor!!,
                        captionTextSize!!
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "error", e)
            } finally {
                a?.recycle()
            }
        }
    }

    private fun startSlide() {
        postDelayed(autoSlideRunnable, slideDuration.toLong())
    }

    fun stopSlide() {
        removeCallbacks(autoSlideRunnable)
    }

    fun setSlideDuration(slideDuration: Int) {
        this.slideDuration = slideDuration
    }

    fun setAutoSlide(autoSlide: Boolean) {
        this.autoSlide = autoSlide
        if (autoSlide)
            startSlide()
        else
            stopSlide()
    }

    fun setImages(images: ArrayList<SliderModel>) {
        slideAdapter.items = images
        slideAdapter.notifyDataSetChanged()
    }

    override fun onDetachedFromWindow() {
        stopSlide()
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (autoSlide) {
            startSlide()
        }
    }
}