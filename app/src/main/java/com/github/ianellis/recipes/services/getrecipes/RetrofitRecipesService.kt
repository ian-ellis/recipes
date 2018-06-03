package com.github.ianellis.recipes.services.getrecipes

import com.github.ianellis.recipes.domain.getrecipes.RecipeService
import com.github.ianellis.recipes.entities.networkresponses.RecipesResponse
import retrofit2.http.GET
import rx.Observable

interface RetrofitRecipesService {

  @GET("recipes.json")
  fun recipes(): Observable<RecipesResponse>

}

fun RetrofitRecipesService.stripRetrofit(): RecipeService {
  val self = this
  return object : RecipeService {
    override fun recipes(): Observable<RecipesResponse> {
      return self.recipes()
    }
  }
}