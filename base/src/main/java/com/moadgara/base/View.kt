package com.moadgara.base

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.setAndStartAnimation(
    @AnimRes animResId: Int,
    animationListenerParameters: AnimationListenerParameters?
) {
    val animation: Animation = AnimationUtils.loadAnimation(context, animResId)
    startAnimation(animation)
    animation.setAnimationListener(animationListenerParameters)
}
