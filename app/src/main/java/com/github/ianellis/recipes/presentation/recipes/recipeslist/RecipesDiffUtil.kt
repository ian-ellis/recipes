package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.support.v7.util.DiffUtil
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal class RecipesDiffUtil(private val old:List<Recipe>, private val new:List<Recipe>) : DiffUtil.Callback() {

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return old[oldItemPosition] == new[newItemPosition]
  }

  override fun getOldListSize(): Int = old.size

  override fun getNewListSize(): Int  = new.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return old[oldItemPosition] == new[newItemPosition]
  }
}