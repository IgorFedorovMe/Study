package me.igorfedorov.kinonline.base.cicerone_navigation.utils

import android.view.View
import android.view.animation.Animation

fun View.startAnimation(animation: Animation, onComplete: () -> Unit) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(p0: Animation?) {
            onComplete()
        }

        override fun onAnimationStart(p0: Animation?) = Unit
        override fun onAnimationRepeat(p0: Animation?) = Unit
    })
    this.startAnimation(animation)
}