package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal abstract class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view){

  abstract fun bind(recipe: Recipe)
}