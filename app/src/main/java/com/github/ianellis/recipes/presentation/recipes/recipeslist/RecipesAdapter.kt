package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal class RecipesAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<RecipeViewHolder>() {

  companion object {
    const val TYPE_IMAGE = 0
    const val TYPE_TEXT = 1
  }

  var recipes: List<Recipe> = emptyList()
    set(value) {
      val diff = DiffUtil.calculateDiff(RecipesDiffUtil(field, value))
      field = value
      diff.dispatchUpdatesTo(this)
    }

  override fun getItemViewType(position: Int): Int {
    return if (recipes[position].image.isEmpty()) {
      TYPE_TEXT
    } else {
      TYPE_IMAGE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
    return if (viewType == TYPE_IMAGE) {
      RecipeImageViewHolder(DataBindingUtil.inflate(inflater, R.layout.recycler_item_recipe_with_image, parent, false))
    } else {
      RecipeTextViewHolder(DataBindingUtil.inflate(inflater, R.layout.recycler_item_recipe_with_text, parent, false))
    }
  }

  override fun getItemCount(): Int = recipes.size

  override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
    holder.bind(recipes[position])
  }
}