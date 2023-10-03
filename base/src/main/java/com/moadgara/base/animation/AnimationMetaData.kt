package com.moadgara.base.animation

data class AnimationMetaData(
    val direction: AnimationDirection = AnimationDirection.LEFT_TO_RIGHT,
    val type: AnimationType = AnimationType.SLIDE_IN,
    val duration: Long = 1000
)
