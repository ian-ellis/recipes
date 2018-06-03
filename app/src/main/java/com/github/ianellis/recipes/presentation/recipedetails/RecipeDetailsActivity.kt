package com.github.ianellis.recipes.presentation.recipedetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.databinding.ActivityRecipeDetailsBinding
import com.github.ianellis.recipes.presentation.BaseActivity
import com.github.ianellis.recipes.presentation.webview.WebViewLauncher

class RecipeDetailsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val recipe = RecipeDetailLauncher.deserialize(from = intent)
    val binding = DataBindingUtil.setContentView<ActivityRecipeDetailsBinding>(this, R.layout.activity_recipe_details)
    binding.recipe = recipe
    binding.linkHandler = { url: String ->
      startActivity(WebViewLauncher.launch(from = this, withUrl = url, andTitle = recipe.title))
      overridePendingTransition(R.anim.slide_in_up, R.anim.no_move)
    }
    supportActionBar?.title = recipe.title
  }

  override fun onBackPressed() {
    super.onBackPressed()
    overridePendingTransition(0, R.anim.slide_out_right)
  }

}