package moadgara.base.animation

import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation

private data class DeltaValues(val fromX: Float, val toX: Float, val fromY: Float, val toY: Float)

fun produceAnimationSetFromMetaData(metaData: AnimationMetaData): AnimationSet {
    val animationSet = AnimationSet(true)

    var fadeAnimation: AlphaAnimation? = null
    var translateAnimation: TranslateAnimation? = null
    var scaleAnimation: ScaleAnimation? = null

    val deltaMap = mapOf(
        listOf("SLIDE_IN", "SLIDE_FADE_IN") to mapOf(
            AnimationDirection.RIGHT_TO_LEFT to DeltaValues(1f, 0f, 0f, 0f),
            AnimationDirection.LEFT_TO_RIGHT to DeltaValues(-1f, 0f, 0f, 0f),
            AnimationDirection.TOP_TO_BOTTOM to DeltaValues(0f, 0f, -1f, 0f),
            AnimationDirection.BOTTOM_TO_TOP to DeltaValues(0f, 0f, 1f, 0f)
        ),
        listOf("SLIDE_OUT", "SLIDE_FADE_OUT") to mapOf(
            AnimationDirection.RIGHT_TO_LEFT to DeltaValues(0f, -1f, 0f, 0f),
            AnimationDirection.LEFT_TO_RIGHT to DeltaValues(0f, 1f, 0f, 0f),
            AnimationDirection.TOP_TO_BOTTOM to DeltaValues(0f, 0f, 0f, 1f),
            AnimationDirection.BOTTOM_TO_TOP to DeltaValues(0f, 0f, 0f, -1f)
        )
    )

    val type = metaData.type.name
    val direction = metaData.direction

    val itemMatch = deltaMap.keys.find { it.contains(type) }
    if (itemMatch != null) {
        val directionMap = deltaMap[itemMatch]
        if (directionMap?.containsKey(direction) == true) {
            val deltaValues = directionMap[direction] ?: DeltaValues(0f, 0f, 0f, 0f)

            translateAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                deltaValues.fromX,
                Animation.RELATIVE_TO_PARENT,
                deltaValues.toX,
                Animation.RELATIVE_TO_PARENT,
                deltaValues.fromY,
                Animation.RELATIVE_TO_PARENT,
                deltaValues.toY
            )

            translateAnimation.fillAfter = true
            translateAnimation.duration = metaData.duration
        }
    }


    if (metaData.type.name.contains("FADE_IN")) {
        fadeAnimation = AlphaAnimation(0f, 1f)
        fadeAnimation.interpolator = DecelerateInterpolator()
    } else if (metaData.type.name.contains("FADE_OUT")) {
        fadeAnimation = AlphaAnimation(1f, 0f)
        fadeAnimation.interpolator = AccelerateInterpolator()
    }

    if (metaData.type == AnimationType.SCALE_UP) {
        scaleAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
    } else if (metaData.type == AnimationType.SCALE_DOWN) {
        scaleAnimation = ScaleAnimation(1f, 1f, 1f, 0f)
    }

    if (fadeAnimation != null) {
        fadeAnimation.duration = metaData.duration
        fadeAnimation.fillAfter = true
        animationSet.addAnimation(fadeAnimation)
    }

    if (translateAnimation != null) {
        animationSet.addAnimation(translateAnimation)
    }

    if (scaleAnimation != null) {
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = metaData.duration
        animationSet.addAnimation(scaleAnimation)
    }

    return animationSet
}
