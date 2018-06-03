package com.github.ianellis.recipes.presentation.recipes.recipeslist

import com.github.ianellis.recipes.databinding.RecyclerItemRecipeWithImageBinding
import com.github.ianellis.recipes.domain.getrecipes.Recipe

internal class RecipeImageViewHolder(
    private val binding: RecyclerItemRecipeWithImageBinding
) : RecipeViewHolder(binding.root) {

  override fun bind(recipe: Recipe) {
    binding.recipe = recipe
  }
}