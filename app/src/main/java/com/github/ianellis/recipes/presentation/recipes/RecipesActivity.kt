package com.github.ianellis.recipes.presentation.recipes

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.databinding.ActivityRecipesBinding
import com.github.ianellis.recipes.presentation.BaseActivity
import com.github.ianellis.recipes.presentation.recipedetails.RecipeDetailLauncher
import javax.inject.Inject

class RecipesActivity : BaseActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val vm = ViewModelProviders.of(this, viewModelFactory)[RecipesViewModel::class.java]
    val binding = DataBindingUtil.setContentView<ActivityRecipesBinding>(this, R.layout.activity_recipes)
    binding.setLifecycleOwner(this)
    binding.viewModel = vm

    vm.openRecipeActivity = { recipe ->
      startActivity(RecipeDetailLauncher.launch(from = this, with = recipe))
      overridePendingTransition(R.anim.slide_in_left, R.anim.no_move)
    }

  }
}
