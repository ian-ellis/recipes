package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.view.View
import com.github.ianellis.recipes.databinding.RecyclerItemRecipeWithTextBinding
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal class RecipeTextViewHolder(
    private val binding: RecyclerItemRecipeWithTextBinding,
    private val selected: ((Recipe) -> Unit)?
) : RecipeViewHolder(binding.root) {

  override fun bind(recipe: Recipe) {
    binding.recipe = recipe
    binding.onClick = View.OnClickListener { _ ->
      selected?.invoke(recipe)
    }
  }

}