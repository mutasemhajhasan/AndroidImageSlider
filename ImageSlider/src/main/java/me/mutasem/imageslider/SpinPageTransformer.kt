package me.mutasem.imageslider

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class SpinPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.getWidth()
        if (abs(position) < 0.5) {
            page.visibility = View.VISIBLE
            page.scaleX = 1 - Math.abs(position)
            page.scaleY = 1 - Math.abs(position)
        } else if (Math.abs(position) > 0.5) {
            page.visibility = View.GONE
        }
        when {
            position < -1 -> {  // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f
            }
            position <= 0 -> {    // [-1,0]
                page.alpha = 1f
                page.rotation = 360 * (1 - Math.abs(position))
            }
            position <= 1 -> {    // (0,1]
                page.alpha = 1f
                page.rotation = -360 * (1 - Math.abs(position))
            }
            else -> {  // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
            }
        }
    }
}