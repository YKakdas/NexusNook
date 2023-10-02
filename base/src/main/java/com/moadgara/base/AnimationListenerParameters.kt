package com.moadgara.base

data class AnimationListenerParameters(
    var onStart: (() -> Unit)? = null,
    var onEnd: (() -> Unit)? = null,
    var onRepeat: (() -> Unit)? = null
)
