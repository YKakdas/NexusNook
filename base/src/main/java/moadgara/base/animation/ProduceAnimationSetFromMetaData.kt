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

    val translateAnimation: TranslateAnimation? = checkTranslationAnimation(metaData)
    val fadeAnimation: AlphaAnimation? = checkFadeAnimation(metaData)
    val scaleAnimation: ScaleAnimation? = checkScaleAnimation(metaData)

    translateAnimation?.let { animationSet.addAnimation(it) }
    fadeAnimation?.let { animationSet.addAnimation(it) }
    scaleAnimation?.let { animationSet.addAnimation(it) }

    return animationSet
}

fun checkTranslationAnimation(metaData: AnimationMetaData): TranslateAnimation? {
    val type = metaData.type.name
    val direction = metaData.direction
    val directionMap = getDirectionMap(type)
    var translateAnimation: TranslateAnimation? = null
    if (directionMap != null && directionMap.containsKey(direction)) {
        val deltaValues = directionMap[direction] ?: DeltaValues(0f, 0f, 0f, 0f)
        translateAnimation = createTranslateAnimation(deltaValues, metaData.duration)
    }

    return translateAnimation
}

private fun createTranslateAnimation(deltaValues: DeltaValues, duration: Long): TranslateAnimation? {
    val translateAnimation = TranslateAnimation(
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
    translateAnimation.duration = duration

    return translateAnimation
}

private fun getDirectionMap(type: String): Map<AnimationDirection, DeltaValues>? {
    val directionMap = when (type) {
        in listOf("SLIDE_IN", "SLIDE_FADE_IN") -> getAnimationInDirectionToDeltaMap()
        in listOf("SLIDE_OUT", "SLIDE_FADE_OUT") -> getAnimationOutDirectionToDeltaMap()
        else -> null
    }
    return directionMap
}

private fun checkFadeAnimation(metaData: AnimationMetaData): AlphaAnimation? {
    var fadeAnimation: AlphaAnimation? = null
    if (metaData.type.name.contains("FADE_IN")) {
        fadeAnimation = AlphaAnimation(0f, 1f); fadeAnimation.interpolator = DecelerateInterpolator()
    } else if (metaData.type.name.contains("FADE_OUT")) {
        fadeAnimation = AlphaAnimation(1f, 0f); fadeAnimation.interpolator = AccelerateInterpolator()
    }
    fadeAnimation?.duration = metaData.duration
    fadeAnimation?.fillAfter = true
    return fadeAnimation
}

private fun checkScaleAnimation(metaData: AnimationMetaData): ScaleAnimation? {
    var scaleAnimation: ScaleAnimation? = null
    if (metaData.type == AnimationType.SCALE_UP) {
        scaleAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
    } else if (metaData.type == AnimationType.SCALE_DOWN) {
        scaleAnimation = ScaleAnimation(1f, 1f, 1f, 0f)
    }
    scaleAnimation?.fillAfter = true
    scaleAnimation?.duration = metaData.duration
    return scaleAnimation
}

private fun getAnimationInDirectionToDeltaMap() = mapOf(
  AnimationDirection.RIGHT_TO_LEFT to DeltaValues(1f, 0f, 0f, 0f),
  AnimationDirection.LEFT_TO_RIGHT to DeltaValues(-1f, 0f, 0f, 0f),
  AnimationDirection.TOP_TO_BOTTOM to DeltaValues(0f, 0f, -1f, 0f),
  AnimationDirection.BOTTOM_TO_TOP to DeltaValues(0f, 0f, 1f, 0f)
)

private fun getAnimationOutDirectionToDeltaMap() = mapOf(
  AnimationDirection.RIGHT_TO_LEFT to DeltaValues(0f, -1f, 0f, 0f),
  AnimationDirection.LEFT_TO_RIGHT to DeltaValues(0f, 1f, 0f, 0f),
  AnimationDirection.TOP_TO_BOTTOM to DeltaValues(0f, 0f, 0f, 1f),
  AnimationDirection.BOTTOM_TO_TOP to DeltaValues(0f, 0f, 0f, -1f)
)