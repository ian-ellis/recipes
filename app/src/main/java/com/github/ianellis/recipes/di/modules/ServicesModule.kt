package com.github.ianellis.recipes.di.modules

import com.github.ianellis.recipes.domain.getrecipes.RecipeService
import com.github.ianellis.recipes.services.ServiceFactory
import com.github.ianellis.recipes.services.getrecipes.RetrofitRecipesService
import com.github.ianellis.recipes.services.getrecipes.stripRetrofit
import dagger.Module
import dagger.Provides

@Module
class ServicesModule {

  companion object {
    private const val ENDPOINT = "https://g525204.github.io/"
  }

  @Provides
  fun providesRecipeService(factory: ServiceFactory): RecipeService {
    val retroFit = factory.createService(RetrofitRecipesService::class.java, ENDPOINT)
    return retroFit.stripRetrofit()
  }

}