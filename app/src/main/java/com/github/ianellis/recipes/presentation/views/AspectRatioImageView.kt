package com.github.ianellis.recipes.presentation.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import com.github.ianellis.recipes.R


class AspectRatioImageView : AppCompatImageView {

  private enum class Direction(val value: Int) {
    WIDTH(0),
    HEIGHT(1)
  }

  var ratio: Float
    set(value) {
      if (value > 0) {
        field = value
        requestLayout()
      }
    }

  var onPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null
    set(value) {
      field?.let {
        viewTreeObserver.removeOnPreDrawListener(it)
      }
      field = value
      viewTreeObserver.addOnPreDrawListener(value)
    }

  private var ratioDirection = Direction.HEIGHT

  init {
    ratio = -1f
  }

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    readAttributes(attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    readAttributes(attrs)
  }

  private fun readAttributes(attrs: AttributeSet) {
    val attributeArray = context.obtainStyledAttributes(
        attrs,
        R.styleable.AspectRatioImageView)

    ratioDirection = when (attributeArray.getInteger(R.styleable.AspectRatioImageView_ratioDirection, -1)) {
      Direction.HEIGHT.value -> Direction.HEIGHT
      Direction.WIDTH.value -> Direction.WIDTH
      else -> Direction.HEIGHT
    }
    ratio = attributeArray.getFloat(R.styleable.AspectRatioImageView_ratio, -1f)

    attributeArray.recycle()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    if (ratio > 0) { // a negative ratio actually makes no sense

      when (ratioDirection) {
        Direction.HEIGHT -> {
          val width = View.MeasureSpec.getSize(widthMeasureSpec)
          val height = Math.ceil((width * ratio).toDouble()).toInt()
          setMeasuredDimension(width, height)
        }
        Direction.WIDTH -> {
          val height = View.MeasureSpec.getSize(heightMeasureSpec)
          val width = Math.ceil((height * ratio).toDouble()).toInt()
          setMeasuredDimension(width, height)
        }
      }
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }
}