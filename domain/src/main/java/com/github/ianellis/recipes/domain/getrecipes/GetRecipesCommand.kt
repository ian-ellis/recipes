package com.github.ianellis.recipes.domain.getrecipes

import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.entities.RecipeEntity
import rx.Observable
import javax.inject.Inject

internal class GetRecipesCommand @Inject constructor(
    private val repository: RecipesRepository
) : () -> Observable<ReactiveList<Recipe>> {

  override fun invoke(): Observable<ReactiveList<Recipe>> {
    return repository.recipes.map { reactiveEntities ->
      reactiveEntities.process(sortAndMapEntitiesByImageAvailable)
    }
  }

  private val sortAndMapEntitiesByImageAvailable: (List<RecipeEntity>) -> List<Recipe> = { entities ->
    entities.asSequence()
        .map(entityToDataObject)
        .sortedBy { it.image.isEmpty() }
        .toList()
  }

  private val entityToDataObject: (RecipeEntity) -> Recipe = { entity ->
    Recipe(
        title = entity.title,
        link = entity.link,
        ingredients = listIngredients(entity.ingredients),
        image = entity.image
    )
  }

  private fun listIngredients(ingredients: String): List<String> {
    return ingredients.split(",")
        .asSequence()
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .toList()
  }

}