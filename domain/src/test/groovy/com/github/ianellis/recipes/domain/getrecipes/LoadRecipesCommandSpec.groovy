package com.github.ianellis.recipes.domain.getrecipes

import spock.lang.Specification

class LoadRecipesCommandSpec extends Specification {

  private LoadRecipesCommand loadRecipes
  private RecipesRepository repository

  def setup(){
    repository = Mock RecipesRepository
    loadRecipes = new LoadRecipesCommand(repository)
  }

  def 'invoke() - invokes refresh on repository'(){
    when:
    loadRecipes.invoke()

    then:
    1 * repository.refresh()
  }
}