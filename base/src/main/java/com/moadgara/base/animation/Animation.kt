package com.moadgara.base.animation

import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

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
