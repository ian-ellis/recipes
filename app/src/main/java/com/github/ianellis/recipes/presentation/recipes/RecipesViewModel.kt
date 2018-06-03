package com.github.ianellis.recipes.presentation.recipes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.Resources
import android.support.annotation.VisibleForTesting
import android.view.View
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.di.qualifiers.MainThreadScheduler
import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.domain.getrecipes.Recipe
import com.github.ianellis.recipes.domain.getrecipes.di.GetRecipes
import com.github.ianellis.recipes.domain.getrecipes.di.LoadRecipes
import com.github.ianellis.recipes.presentation.data.LoadingStatus
import rx.Observable
import rx.Scheduler
import rx.Subscription
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    @GetRecipes getRecipes: () -> Observable<ReactiveList<Recipe>>,
    @LoadRecipes private val refreshRecipes: () -> Unit,
    @MainThreadScheduler mainThread: Scheduler,
    private val resources: Resources
) : ViewModel() {

  val status = MutableLiveData<LoadingStatus>()
  val recipes = MutableLiveData<List<Recipe>>()

  var openRecipeActivity:((Recipe)->Unit)? = null

  @VisibleForTesting
  private val recipesSubscription: Subscription

  init {
    status.value = LoadingStatus.Loading()
    recipesSubscription = getRecipes().observeOn(mainThread).subscribe({
      if (it.hasError() && !it.hasValue()) {
        displayError()
      } else {
        displayRecipes(it.getValue())
      }
    })

    refreshRecipes()
  }

  fun refresh(v: View) {
    status.value = LoadingStatus.Loading()
    refreshRecipes()
  }

  val recipeSelected = {recipe:Recipe ->
    openRecipeActivity?.invoke(recipe)
  }

  private fun displayError() {
    status.value = LoadingStatus.Error(resources.getString(R.string.recipes_load_error_message))
  }

  private fun displayRecipes(recipes: List<Recipe>) {
    status.value = LoadingStatus.Loaded()
    this.recipes.value = recipes
  }

  override fun onCleared() {
    recipesSubscription.unsubscribe()
    super.onCleared()
  }

}