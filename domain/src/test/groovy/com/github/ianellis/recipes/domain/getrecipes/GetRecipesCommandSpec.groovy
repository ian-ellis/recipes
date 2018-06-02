package com.github.ianellis.recipes.domain.getrecipes

import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.entities.RecipeEntity
import rx.Observable
import rx.observers.TestSubscriber
import spock.lang.Specification

class GetRecipesCommandSpec extends Specification {

  private RecipesRepository repository
  private GetRecipesCommand getRecipes
  private TestSubscriber<ReactiveList<Recipe>> subscriber

  def setup() {
    repository = Mock RecipesRepository
    getRecipes = new GetRecipesCommand(repository)
    subscriber = new TestSubscriber<>()
  }

  def 'execute() - maps entities from repository to data object, and passes on errors'() {
    given:
    def entities = [
        new RecipeEntity("one", "linkone", "eggs,flour", "imageone"),
        new RecipeEntity("two", "linktwo", "cheese , bread", "imagetwo"),
        new RecipeEntity("three", "linkthree", "onion", "imagethree"),
        new RecipeEntity("four", "linkfour", "", "imagefour")
    ]

    repository.getRecipes() >> Observable.just(new ReactiveList<>(entities, originalError))
    when:
    getRecipes.invoke().subscribe(subscriber)

    then:
    subscriber.onNextEvents.size() == 1
    subscriber.onNextEvents.first().error == originalError
    subscriber.onNextEvents.first().value.size() == 4

    def firstMapped = subscriber.onNextEvents.first().value[0]
    assertMapped(firstMapped, "one", "linkone", ["eggs", "flour"], "imageone")

    def secondMapped = subscriber.onNextEvents.first().value[1]
    assertMapped(secondMapped, "two", "linktwo", ["cheese", "bread"], "imagetwo")

    def thirdMapped = subscriber.onNextEvents.first().value[2]
    assertMapped(thirdMapped, "three", "linkthree", ["onion"], "imagethree")

    def fourthMapped = subscriber.onNextEvents.first().value[3]
    assertMapped(fourthMapped, "four", "linkfour", [], "imagefour")

    where:
    originalError << [null, new Exception("Ooops")]
  }

  def 'execute() - sorts recipes with those with images first'(){
    def entities = [
        new RecipeEntity("one", "linkone", "eggs,flour", "imageone"),
        new RecipeEntity("two", "linktwo", "cheese , bread", ""),
        new RecipeEntity("three", "linkthree", "onion", "imagethree"),
        new RecipeEntity("four", "linkfour", "", "")
    ]
    repository.getRecipes() >> Observable.just(new ReactiveList<>(entities))

    when:
    getRecipes.invoke().subscribe(subscriber)

    then:
    subscriber.onNextEvents.first().error == null
    subscriber.onNextEvents.first().value.size() == 4

    def firstMapped = subscriber.onNextEvents.first().value[0]
    assertMapped(firstMapped, "one", "linkone", ["eggs", "flour"], "imageone")

    def secondMapped = subscriber.onNextEvents.first().value[1]
    assertMapped(secondMapped, "three", "linkthree", ["onion"], "imagethree")

    def thirdMapped = subscriber.onNextEvents.first().value[2]
    assertMapped(thirdMapped, "two", "linktwo", ["cheese", "bread"], "")

    def fourthMapped = subscriber.onNextEvents.first().value[3]
    assertMapped(fourthMapped, "four", "linkfour", [], "")
  }

  private static void assertMapped(Recipe recipe, String title, String link, List<String> ingredients, String image) {

    assert recipe.title == title
    assert recipe.link == link
    assert recipe.ingredients == ingredients
    assert recipe.image == image

  }
}