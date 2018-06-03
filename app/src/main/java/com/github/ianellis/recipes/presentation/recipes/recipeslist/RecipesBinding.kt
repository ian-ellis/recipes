package com.github.ianellis.recipes.presentation.recipes.recipeslist

import android.databinding.BindingAdapter
import com.github.ianellis.recipes.domain.getrecipes.Recipe
import com.github.ianellis.recipes.presentation.recipes.recipeslist.RecipesRecycler

object RecipesBinding {

  @JvmStatic
  @BindingAdapter("recipes")
  fun bindRecipes(recycler: RecipesRecycler, recipes: List<Recipe>?) {
    recycler.setRecipes(recipes ?: emptyList())
  }

  @JvmStatic
  @BindingAdapter("onSelected")
  fun bindRecipes(recycler: RecipesRecycler, onSelected: (Recipe)->Unit) {
    recycler.setRecipeSelected(onSelected)
  }

}