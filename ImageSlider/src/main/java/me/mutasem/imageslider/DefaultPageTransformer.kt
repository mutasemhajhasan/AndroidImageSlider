package me.mutasem.imageslider

import android.os.Build
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DefaultPageTransformer : ViewPager2.PageTransformer {
    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f

    }

    override fun transformPage(view: View, position: Float) {
        view.apply {

            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationX = 0f
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        translationZ = 0f
                    }
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    alpha = 1f
                    translationX = 0f
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        translationZ = 0f
                    }
                    scaleX = 1f
                    scaleY = 1f
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }



}