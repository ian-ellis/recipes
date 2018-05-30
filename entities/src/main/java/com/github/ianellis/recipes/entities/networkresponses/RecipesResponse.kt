package com.github.ianellis.recipes.entities.networkresponses

import com.github.ianellis.recipes.entities.RecipeEntity
import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("results") val recipes: List<RecipeEntity>
)