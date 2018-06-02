package com.github.ianellis.recipes.domain.getrecipes

import com.github.ianellis.recipes.entities.networkresponses.RecipesResponse
import rx.Observable

interface RecipeService {

  fun recipes(): Observable<RecipesResponse>

}