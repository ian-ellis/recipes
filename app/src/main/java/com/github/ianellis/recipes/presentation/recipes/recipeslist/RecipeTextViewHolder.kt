package com.github.ianellis.recipes.presentation.recipes.recipeslist

import com.github.ianellis.recipes.databinding.RecyclerItemRecipeWithTextBinding
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal class RecipeTextViewHolder(
    private val binding: RecyclerItemRecipeWithTextBinding
) : RecipeViewHolder(binding.root) {

  override fun bind(recipe: Recipe) {
    binding.recipe = recipe
  }

}