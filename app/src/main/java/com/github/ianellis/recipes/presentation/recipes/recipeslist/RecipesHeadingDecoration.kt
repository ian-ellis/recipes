package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.presentation.recipes.recipeslist.RecipesAdapter.Companion.TYPE_IMAGE
import com.github.ianellis.recipes.presentation.recipes.recipeslist.RecipesAdapter.Companion.TYPE_TEXT
import com.github.ianellis.recipes.presentation.recipes.recipeslist.RecipesAdapter.Companion.TYPE_UNKNOWN

internal class RecipesHeadingDecoration(
    private val columns: Int,
    private val spacing: Int,
    context: Context,
    parent: ViewGroup
) : RecyclerView.ItemDecoration() {

  private val popularHeading: View
  private val otherHeading: View

  init {
    val inflater = LayoutInflater.from(context)

    val populatHeadingText = context.resources.getString(R.string.recipes_heading_popular)
    popularHeading = createHeader(populatHeadingText, inflater, parent)

    val otherHeadingText = context.resources.getString(R.string.recipes_heading_other)
    otherHeading = createHeader(otherHeadingText, inflater, parent)

  }

  private fun createHeader(heading: String, inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.recycler_header_recipes, parent, false)
    view.findViewById<TextView>(R.id.txt_header).text = heading
    view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    return view
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    headerOrNull(view, parent)?.let {
      outRect.top += it.measuredHeight - spacing
    }
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
    super.onDraw(c, parent, state)

    (0 until parent.childCount).asSequence()
        .map { parent.getChildAt(it) }
        .map { Pair(it, headerOrNull(it, parent)) }
        .filter { it.second != null }
        .forEach {
          drawHeader(c, it.first, it.second!!, parent)
        }

  }

  private fun headerOrNull(view: View, parent: RecyclerView): View? {
    val position = parent.getChildAdapterPosition(view)
    val type = (parent.adapter as? RecipesAdapter)?.getItemViewType(position) ?: TYPE_UNKNOWN

    val previousTypeDeduction = getPreviousTypeDeduction(position, parent)
    val previousType = parent.adapter.getItemViewType(position - previousTypeDeduction)

    val heading = when (type) {
      TYPE_IMAGE -> popularHeading
      TYPE_TEXT -> otherHeading
      else -> null
    }

    return if (type != previousType) {
      heading
    } else {
      null
    }
  }

  private fun getPreviousTypeDeduction(position: Int, parent: RecyclerView): Int {
    val spanSize = (parent.layoutManager as? GridLayoutManager)?.spanSizeLookup?.getSpanSize(position)
        ?: 1
    return columns / spanSize
  }

  private fun drawHeader(c: Canvas, recyclerItemView: View, header: View, parent: RecyclerView) {
    c.drawColor(Color.TRANSPARENT)

    header.layout(
        parent.x.toInt(),
        recyclerItemView.top,
        (parent.x + parent.width).toInt(),
        recyclerItemView.top + header.measuredHeight
    )

    c.save()
    c.translate(0f, recyclerItemView.top.toFloat() - header.measuredHeight)

    header.draw(c)
    c.restore()

  }

}