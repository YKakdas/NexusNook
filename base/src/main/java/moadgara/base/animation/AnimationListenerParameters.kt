package moadgara.base.animation

data class AnimationListenerParameters(
    var onStart: (() -> Unit)? = null,
    var onEnd: (() -> Unit)? = null,
    var onRepeat: (() -> Unit)? = null
)
