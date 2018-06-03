package com.github.ianellis.recipes.presentation.recipedetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.github.ianellis.recipes.R

class IngredientsList : LinearLayout {

  var ingredients: List<String> = emptyList()
    set(value) {
      if (value != field) {
        addIngredients(value)
      }
      field = value
    }


  constructor (context: Context?) : super(context)
  constructor (context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor (context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

  init {
    orientation = LinearLayout.VERTICAL
  }


  private fun addIngredients(ingredients: List<String>) {
    val inflater = LayoutInflater.from(context)
    removeAllViews()
    ingredients.forEach { ingredient ->
      val view = inflater.inflate(R.layout.view_ingredient, this, false) as? TextView
      view?.let {
        it.text = ingredient
        addView(view)
      }
    }

  }
}