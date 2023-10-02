package com.moadgara.base

import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun Animation.setAnimationListener(animationListenerParameters: AnimationListenerParameters?) {
    setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            animationListenerParameters?.onStart?.invoke()
        }

        override fun onAnimationEnd(animation: Animation?) {
            animationListenerParameters?.onEnd?.invoke()
        }

        override fun onAnimationRepeat(animation: Animation?) {
            animationListenerParameters?.onRepeat?.invoke()
        }

    })
}
