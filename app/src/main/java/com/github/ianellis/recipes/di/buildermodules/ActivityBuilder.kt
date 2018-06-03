package com.github.ianellis.recipes.di.buildermodules

import com.github.ianellis.recipes.di.modules.ViewModelModule
import com.github.ianellis.recipes.domain.common.di.scopes.ActivityScope
import com.github.ianellis.recipes.domain.getrecipes.di.GetRecipesModule
import com.github.ianellis.recipes.presentation.recipedetails.RecipeDetailsActivity

import com.github.ianellis.recipes.presentation.recipes.RecipesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

  @ActivityScope
  @ContributesAndroidInjector(modules = [
    ViewModelModule::class,
    GetRecipesModule::class
  ])
  abstract fun bindRecipesActivity(): RecipesActivity

  @ActivityScope
  @ContributesAndroidInjector()
  abstract fun bindRecipeDetailsActivity(): RecipeDetailsActivity

}