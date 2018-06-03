package com.github.ianellis.recipes.presentation.recipedetails

import android.databinding.BindingAdapter

object RecipeDetailsBindings {

  @JvmStatic
  @BindingAdapter("ingredients")
  fun bindIngredients(ingredientsList: IngredientsList, ingredients: List<String>) {
    ingredientsList.ingredients = ingredients
  }

}