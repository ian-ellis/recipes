package com.github.ianellis.recipes.presentation.recipedetails

import android.content.Context
import android.content.Intent
import com.github.ianellis.recipes.domain.getrecipes.Recipe

object RecipeDetailLauncher {
  private const val TITLE_KEY = "title"
  private const val LINK_KEY = "link"
  private const val INGREDIENTS_KEY = "ingredients"
  private const val IMAGE_KEY = "image"

  fun launch(from: Context, with: Recipe): Intent = Intent(from, RecipeDetailsActivity::class.java).apply {
    putExtra(TITLE_KEY, with.title)
    putExtra(LINK_KEY, with.link)
    putExtra(INGREDIENTS_KEY, with.ingredients.toTypedArray())
    putExtra(IMAGE_KEY, with.image)
  }

  internal fun deserialize(from: Intent): Recipe {
    return Recipe(
        title = from.getStringExtra(TITLE_KEY),
        link = from.getStringExtra(LINK_KEY),
        ingredients = from.getStringArrayExtra(INGREDIENTS_KEY).toList(),
        image = from.getStringExtra(IMAGE_KEY)
    )
  }
}