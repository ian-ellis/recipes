package com.github.ianellis.recipes.domain.getrecipes

import com.github.ianellis.recipes.domain.common.di.qualifiers.IoScheduler
import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.domain.data.switchMapReactiveList
import com.github.ianellis.recipes.entities.RecipeEntity
import rx.Observable
import rx.Scheduler
import rx.subjects.BehaviorSubject

internal open class RecipesRepository constructor(
    private val recipesService: RecipeService,
    @IoScheduler private val scheduler: Scheduler
) {

  private val load = BehaviorSubject.create<Unit>()

  open val recipes: Observable<ReactiveList<RecipeEntity>> = load.switchMapReactiveList({
    recipesService.recipes()
        .subscribeOn(scheduler)
        .map { it.recipes }
  })

  open fun refresh() {
    load.onNext(Unit)
  }
}