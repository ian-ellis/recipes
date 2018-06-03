package com.github.ianellis.recipes.presentation.recipes

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.res.Resources
import android.view.View
import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.domain.getrecipes.Recipe
import com.github.ianellis.recipes.presentation.data.LoadingStatus
import kotlin.jvm.functions.Function0
import org.junit.Rule
import org.junit.rules.TestRule
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import spock.lang.Specification
import com.github.ianellis.recipes.R

class RecipesViewModelSpec extends Specification {

  private static final String RECIPES_ERROR = "Ooops, error loading recipes"

  private Function0 getRecipes
  private Function0 refreshRecipes
  private Scheduler scheduler
  private Resources resources

  @Rule
  public TestRule rule = new InstantTaskExecutorRule()

  def setup() {
    resources = Mock(Resources) {
      it.getString(R.string.recipes_load_error_message) >> RECIPES_ERROR
    }
    getRecipes = Mock Function0
    refreshRecipes = Mock Function0
    scheduler = Schedulers.immediate()
  }

  def 'RecipesViewModel - sets initial status to load'() {
    given:
    getRecipes.invoke() >> Observable.never()

    when:
    def viewModel = new RecipesViewModel(getRecipes, refreshRecipes, scheduler, resources)

    then:
    viewModel.status.value instanceof LoadingStatus.Loading
  }

  def 'RecipesViewModel - gets and sets Recipes and updates state to loaded onsuccess, ignores error if value exists'() {
    given:
    def recipes = [mockRecipe("Flan"), mockRecipe("Pasta")]
    getRecipes.invoke() >> Observable.just(new ReactiveList(recipes, error))

    when:
    def viewModel = new RecipesViewModel(getRecipes, refreshRecipes, scheduler, resources)

    then:
    viewModel.status.value instanceof LoadingStatus.Loaded
    viewModel.recipes.value == recipes

    where:
    error << [null, new Exception()]

  }

  def 'RecipesViewModel - updates status on error'() {
    given:
    def error = new Exception()
    getRecipes.invoke() >> Observable.just(new ReactiveList(error))

    when:
    def viewModel = new RecipesViewModel(getRecipes, refreshRecipes, scheduler, resources)

    then:
    viewModel.status.value == new LoadingStatus.Error(RECIPES_ERROR)
    viewModel.recipes.value == null

    where:
    recipes << [
        [mockRecipe("Flan"), mockRecipe("Pasta")],
        null
    ]

  }

  def 'refresh() - invokes refresh recipes'() {
    given:
    getRecipes.invoke() >> Observable.never()
    def viewModel = new RecipesViewModel(getRecipes, refreshRecipes, scheduler, resources)

    when:
    viewModel.refresh(Mock(View))

    then:
    1 * refreshRecipes.invoke()
    viewModel.status.value instanceof LoadingStatus.Loading
  }

  def 'onCleared() - unsubscribes from getRecipes'() {
    given:
    getRecipes.invoke() >> Observable.never()
    def viewModel = new RecipesViewModel(getRecipes, refreshRecipes, scheduler, resources)

    when:
    viewModel.onCleared()

    then:
    viewModel.recipesSubscription.unsubscribed

  }

  private static Recipe mockRecipe(String title) {
    return new Recipe(title, "", [], "")
  }
}