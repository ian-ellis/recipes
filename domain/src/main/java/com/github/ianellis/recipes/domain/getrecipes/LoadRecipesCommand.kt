package com.github.ianellis.recipes.domain.getrecipes

import javax.inject.Inject

internal class LoadRecipesCommand @Inject constructor(
    private val recipesRepository: RecipesRepository
) : () -> Unit {

  override fun invoke() {
    recipesRepository.refresh()
  }

}