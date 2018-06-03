package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

internal class RecipesSpacingDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

  private val innerSpacing: Int = (spacing.toFloat() / 2f).toInt()


  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    if (parent.getChildViewHolder(view) is RecipeImageViewHolder) {
      val position = parent.getChildAdapterPosition(view)
      val column = position % spanCount

      applyHorizontalPadding(column, outRect)
      applyVerticalPadding(position, outRect)
    }
  }

  private fun applyHorizontalPadding(column: Int, outRect: Rect) {
    when (column) {
      0 -> applyFirstColumnPadding(outRect)
      spanCount - 1 -> applyLastColumnPadding(outRect)
      else -> applyMiddleColumnPadding(outRect)
    }
  }

  private fun applyFirstColumnPadding(outRect: Rect) {
    outRect.left = spacing
    outRect.right = innerSpacing
  }

  private fun applyMiddleColumnPadding(outRect: Rect) {
    outRect.left = innerSpacing
    outRect.right = innerSpacing
  }

  private fun applyLastColumnPadding(outRect: Rect) {
    outRect.left = innerSpacing
    outRect.right = spacing
  }

  private fun applyVerticalPadding(position: Int, outRect: Rect) {
    if (position < spanCount) {
      outRect.top = spacing
    }
    outRect.bottom = spacing
  }

}