package me.mutasem.imageslider

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
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
    private lateinit var sliderDots: TabLayout
    lateinit var slideAdapter: SlideAdapter
    lateinit var imageSlides: ViewPager2
    private var transformation = 0
    private var autoSlide = false
    private var showDots = false
    private var slideDuration = 1000
    var captionTextColor: Int? = null
    var captionTextSize: Float? = null
    var captionBgColor: Int? = null
    val autoSlideRunnable = object : Runnable {
        override fun run() {
            if (imageSlides.currentItem == (imageSlides.adapter?.itemCount?.minus(
                    1
                ))
            ) imageSlides.setCurrentItem(0, false)
            else
                imageSlides.setCurrentItem(imageSlides.currentItem + 1, true)

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
        slideAdapter = SlideAdapter()
        imageSlides = findViewById(R.id.pager)
        sliderDots = findViewById(R.id.tabs)
        sliderDots.visibility = if (showDots) View.VISIBLE else View.GONE
        imageSlides.adapter = slideAdapter
//        TabLayoutMediator(sliderDots, imageSlides) { tab, position ->
//            if (position > 0 && position < slideAdapter.itemCount - 1)
//                imageSlides.setCurrentItem(tab.position, false)
//        }.attach()

        if (attrs != null) {
            val a = context?.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.SliderView,
                defStyleAttr,
                defStyleRes
            )
            try {
                if (a != null) {
                    slideDuration = a.getInt(R.styleable.SliderView_slideDuration, 1000)
                    transformation = a.getInt(R.styleable.SliderView_transformation, 0)
                    setTransformation(transformation)
                    showDots = a.getBoolean(R.styleable.SliderView_showDots, true)
                    setShowDots(showDots)
                    autoSlide = a.getBoolean(R.styleable.SliderView_autoSlide, false)
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
                    slideAdapter.recyclerView.setOnTouchListener { _, motionEvent ->
                        if (autoSlide) {
                            if (motionEvent.action == MotionEvent.ACTION_DOWN)
                                stopSlide()
                            if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.action == MotionEvent.ACTION_CANCEL)
                                startSlide()
                        }
                        return@setOnTouchListener false
                    }
                }
                with(imageSlides) {
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageScrollStateChanged(state: Int) {
                            super.onPageScrollStateChanged(state)
                            if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                                if (currentItem == 0)
                                    setCurrentItem(adapter!!.itemCount - 2, false)
                                else if (currentItem == adapter!!.itemCount - 1)
                                    setCurrentItem(1, false)
                            }
                        }

                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            if (position == 0 || position == slideAdapter.itemCount - 1)
                                return
                            val t = sliderDots.getTabAt(position - 1)
                            t?.select()
                        }
                    })
                }
                sliderDots.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        val pos = tab?.position
                        if (pos != null && pos + 1 < slideAdapter.itemCount - 1)
                            imageSlides.setCurrentItem(pos + 1, true)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }
                })
            } catch (e: Exception) {
                Log.e(TAG, "error", e)
            } finally {
                a?.recycle()
            }
        }
    }

    private fun startSlide() {
        stopSlide()
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

    fun setShowDots(showDots: Boolean) {
        this.showDots = showDots
        sliderDots.visibility = if (showDots) View.VISIBLE else View.GONE
    }

    fun setImages(images: ArrayList<SliderModel>) {
        slideAdapter.update(images)
        sliderDots.removeAllTabs()
        repeat(images.size) { sliderDots.addTab(sliderDots.newTab()) }
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

    fun setTransformation(transformation: Int) {
        this.transformation = transformation
        when (transformation) {
            Transformation.Default -> imageSlides.setPageTransformer(DefaultPageTransformer())
            Transformation.Cube -> imageSlides.setPageTransformer(CubeOutPageTransformer())
            Transformation.Spin -> imageSlides.setPageTransformer(SpinPageTransformer())
            Transformation.ZoomOut -> imageSlides.setPageTransformer(ZoomOutPageTransformer())
            else -> {
                imageSlides.setPageTransformer(DefaultPageTransformer())
                this.transformation = 0
            }
        }
    }
}