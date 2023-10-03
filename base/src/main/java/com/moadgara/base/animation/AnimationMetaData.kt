package com.moadgara.base.animation

data class AnimationMetaData(
    var direction: AnimationDirection = AnimationDirection.LEFT_TO_RIGHT,
    var type: AnimationType = AnimationType.SLIDE_IN,
    var duration: Long = 1000
)
