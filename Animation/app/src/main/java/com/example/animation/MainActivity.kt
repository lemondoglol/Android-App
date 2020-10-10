package com.example.animation

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButton()
    }

    private fun setupButton() {
        rotate_button.setOnClickListener {
            rotate()
        }
        shift_button.setOnClickListener {
//            shifting()
            shiftLeftAndRight()
        }
        scale_button.setOnClickListener {
            scale()
        }
        fade_button.setOnClickListener {
            fade()
        }
        color_button.setOnClickListener {
            color()
        }
        start_rain_button.setOnClickListener {
            for (i in 0..5) {
                starRain()
            }
        }
    }

    // extension function
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    private fun rotate() {
        // rotate 360 degree
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        animator.repeatCount = 5
        animator.duration = 1000
        animator.disableViewDuringAnimation(rotate_button)
        animator.start()
    }

    private fun shifting() {
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 0f, 200f)
        animator.repeatCount = 3
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(shift_button)
        animator.start()
    }

    private fun shiftLeftAndRight() {
        val animator = TranslateAnimation(-200f, 200f, 0f, 0f)
        animator.duration = 1000
        animator.repeatCount = 5
        animator.repeatMode = ObjectAnimator.REVERSE
        star.startAnimation(animator)
    }

    private fun scale() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 5f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.repeatCount = 5
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(scale_button)
        animator.start()
    }

    private fun fade() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.repeatCount = 5
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(scale_button)
        animator.start()
    }

    private fun color() {
        val animator = ObjectAnimator.ofArgb(
            star.parent,
            "backgroundColor",
            Color.BLUE,
            Color.RED
        )
        animator.duration = 1000
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(color_button)
        animator.start()
    }

    private fun starRain() {
        val container = star.parent as ViewGroup
        val containerWidth = container.width
        val containerHeight = container.height
        var starWidth = star.width.toFloat()
        var starHeight = star.height.toFloat()

        // create new star, and add to parent view
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(android.R.drawable.btn_star_big_on)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        // resize the star
        newStar.scaleX = Math.random().toFloat() * 1.5f + .3f
        newStar.scaleY = newStar.scaleX
        starWidth *= newStar.scaleX
        starHeight *= newStar.scaleY

        // randomize the born place
        newStar.translationX = Math.random().toFloat() * containerWidth - starWidth

        // animation
        val fallAnimator = ObjectAnimator.ofFloat(
            newStar,
            View.TRANSLATION_Y,
            -starHeight,
            containerHeight + starHeight
        )
        fallAnimator.interpolator = AccelerateInterpolator(1f) // mimic gravity
        val rotateAnimator = ObjectAnimator.ofFloat(
            newStar,
            View.ROTATION,
            (Math.random() * 1080).toFloat()
        )
        rotateAnimator.interpolator = LinearInterpolator()

        val animationSet = AnimatorSet()
        animationSet.playTogether(fallAnimator, rotateAnimator)
        animationSet.duration = (Math.random() * 1500 + 2500).toLong()

        animationSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        animationSet.start()
    }
}
