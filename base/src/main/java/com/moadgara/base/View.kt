package com.moadgara.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import com.moadgara.base.animation.AnimationListenerParameters
import com.moadgara.base.animation.AnimationMetaData
import com.moadgara.base.animation.produceAnimationSetFromMetaData
import com.moadgara.base.animation.setAnimationListener

fun View.setAndStartAnimation(
    @AnimRes animResId: Int,
    animationListenerParameters: AnimationListenerParameters?
) {
    val animation: Animation = AnimationUtils.loadAnimation(context, animResId)
    startAnimation(animation)
    animation.setAnimationListener(animationListenerParameters)
}

fun View.setAndStartAnimation(
    animationMetaData: AnimationMetaData,
    animationListenerParameters: AnimationListenerParameters?
) {
    val animationSet = produceAnimationSetFromMetaData(animationMetaData)
    startAnimation(animationSet)
    animation.setAnimationListener(animationListenerParameters)
}

fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, 0)
}

fun View.closeSoftKeyboard(): Boolean {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    if (imm != null && imm.isActive) {
        return imm.hideSoftInputFromWindow(windowToken, 0)
    }
    return false
}
