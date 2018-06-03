package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.domain.getrecipes.Recipe

class RecipesRecycler : RecyclerView {

  private val recipesAdapter = RecipesAdapter(LayoutInflater.from(context))

  private val columns by lazy {
    resources.getInteger(R.integer.recipes_columns)
  }

  constructor (context: Context?) : super(context)
  constructor (context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor (context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

  fun setRecipes(recipes: List<Recipe>) {
    recipesAdapter.recipes = recipes
  }

  fun setRecipeSelected(onSelect: (Recipe) -> Unit) {
    recipesAdapter.recipeSelected = onSelect
  }

  init {
    adapter = recipesAdapter
    val gridLayout = GridLayoutManager(context, columns)
    gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
          RecipesAdapter.TYPE_TEXT -> columns
          else -> 1
        }
      }
    }
    layoutManager = gridLayout
    addItemDecoration(RecipesSpacingDecoration(columns, resources.getDimensionPixelSize(R.dimen.recipe_spacing)))
  }

}