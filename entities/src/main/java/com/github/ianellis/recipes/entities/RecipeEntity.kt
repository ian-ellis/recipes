package com.github.ianellis.recipes.entities

import com.google.gson.annotations.SerializedName

data class RecipeEntity(
    @SerializedName("title") val title: String,
    @SerializedName("href") val link: String,
    @SerializedName("ingredients") val ingredients: String,
    @SerializedName("thumbnail") val image: String
)