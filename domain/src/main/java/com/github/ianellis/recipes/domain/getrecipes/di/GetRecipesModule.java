package com.github.ianellis.recipes.domain.getrecipes.di;

import com.github.ianellis.recipes.domain.common.di.qualifiers.IoScheduler;
import com.github.ianellis.recipes.domain.common.di.scopes.ActivityScope;
import com.github.ianellis.recipes.domain.data.ReactiveList;
import com.github.ianellis.recipes.domain.getrecipes.GetRecipesCommand;
import com.github.ianellis.recipes.domain.getrecipes.LoadRecipesCommand;
import com.github.ianellis.recipes.domain.getrecipes.Recipe;
import com.github.ianellis.recipes.domain.getrecipes.RecipeService;
import com.github.ianellis.recipes.domain.getrecipes.RecipesRepository;
import com.github.ianellis.recipes.domain.getrecipes.di.GetRecipes;
import com.github.ianellis.recipes.domain.getrecipes.di.LoadRecipes;

import dagger.Module;
import dagger.Provides;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import rx.Observable;
import rx.Scheduler;

@Module
public class GetRecipesModule {

  @Provides
  @GetRecipes
  public Function0<? extends Observable<ReactiveList<Recipe>>> provideGetRecipes(GetRecipesCommand getRecipes) {
    return getRecipes;
  }

  @Provides
  @LoadRecipes
  public Function0<Unit> provideLoadRecipes(LoadRecipesCommand loadRecipes) {
    return loadRecipes;
  }

  @Provides
  @ActivityScope
  public RecipesRepository provideRecipesRepository(
      RecipeService recipesService,
      @IoScheduler Scheduler scheduler
  ) {
    return new RecipesRepository(recipesService, scheduler);
  }
}
