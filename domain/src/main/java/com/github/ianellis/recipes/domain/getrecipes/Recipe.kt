package com.github.ianellis.recipes.domain.getrecipes

data class Recipe(
    val title: String,
    val link: String,
    val ingredients: List<String>,
    val image: String
)