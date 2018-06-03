package com.github.ianellis.recipes.presentation.bindings

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.databinding.BindingAdapter
import android.view.View
import android.view.ViewPropertyAnimator
import java.util.WeakHashMap

object Bindings {

  private const val FADE_VISIBLE_DURATION = 500L

  private val animators = WeakHashMap<View, ViewPropertyAnimator>()

  @JvmStatic
  @BindingAdapter("fadeVisible")
  fun bindFadeVisible(view: View, visible: Boolean) {

    val previousAnimator = animators[view]


    if (previousAnimator == null) {
      view.visibility = if (visible) View.VISIBLE else View.GONE
      view.alpha = if (visible) 1f else 0f
      animators[view] = view.animate()
    } else {

      previousAnimator.cancel()

      if (visible) {
        view.visibility = View.VISIBLE
        animators[view] = view.animate().setDuration(FADE_VISIBLE_DURATION).alpha(1f).setListener(null)
      } else {
        animators[view] = view.animate().setDuration(FADE_VISIBLE_DURATION).alpha(0f).setListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            view.visibility = View.GONE
          }
        })
      }
    }
  }

}