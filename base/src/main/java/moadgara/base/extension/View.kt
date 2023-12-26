package moadgara.base.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import moadgara.base.util.PaddingHolder
import moadgara.base.animation.AnimationListenerParameters
import moadgara.base.animation.AnimationMetaData
import moadgara.base.animation.produceAnimationSetFromMetaData
import moadgara.base.animation.setAnimationListener

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

fun View.doOnApplyWindowInsets(actionOnApplyWindowInsets: (View, WindowInsets, PaddingHolder) -> Unit) {
    val initialPadding = this.let {
        PaddingHolder(it.paddingLeft, it.paddingTop, it.paddingRight, it.paddingBottom)
    }

    setOnApplyWindowInsetsListener { v, insets ->
        actionOnApplyWindowInsets(v, insets, initialPadding)
        insets
    }

    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
