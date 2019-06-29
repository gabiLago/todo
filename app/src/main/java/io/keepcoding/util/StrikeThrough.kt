package io.keepcoding.util

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class StrikeThrough {

    companion object {

         fun applyStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

                if (animate) {
                    ValueAnimator.ofInt(content.length).apply {
                        duration = 300
                        interpolator = FastOutSlowInInterpolator()
                        addUpdateListener {
                            span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            view.text = span
                        }
                    }.start()
                } else {

                    span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    view.text = span
            }
         }

         fun removeStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

                if (animate) {
                    ValueAnimator.ofInt(content.length, 0).apply {
                        duration = 300
                        interpolator = FastOutSlowInInterpolator()
                        addUpdateListener {
                            span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            view.text = span
                        }
                    }.start()
                } else {
                    view.text = content
                }
         }
    }
}