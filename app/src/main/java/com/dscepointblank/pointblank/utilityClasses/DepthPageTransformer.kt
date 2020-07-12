package com.dscepointblank.pointblank.utilityClasses

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_forum.view.*
import kotlin.math.abs


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class DepthPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {

        val pageWidth: Int = view.width
        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            }
            position <= 0 -> { // [-1,0]
                // Use the default slide transition when moving to the left page

                view.alpha = 1f
                view.translationX = 0f
                view.translationZ = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
            position <= 1 -> { // (0,1]
                // Fade the page out.
                view.alpha = 1 - position
                // Counteract the default slide transition
                view.translationX = pageWidth * -position
                // Move it behind the left page
                view.translationZ = -1f

                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - abs(
                    position
                )))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
    }

}