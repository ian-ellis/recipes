package com.github.ianellis.recipes.domain.getrecipes
import com.github.ianellis.recipes.domain.data.ReactiveList
import com.github.ianellis.recipes.entities.RecipeEntity
import com.github.ianellis.recipes.entities.networkresponses.RecipesResponse
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import spock.lang.Specification

class RecipesRepositorySpec extends Specification {

  private RecipesRepository repository
  private RecipeService service
  private TestSubscriber<ReactiveList<RecipeEntity>> subscriber

  def setup() {
    service = Mock RecipeService
    repository = new RecipesRepository(service, Schedulers.immediate())
    subscriber = new TestSubscriber<>()
  }

  def 'refresh() - loads recipes, dropping pending result'() {
    given:
    def entities = [mockRecipe(), mockRecipe()]
    def delayedStream = BehaviorSubject.create()
    def secondStream = BehaviorSubject.create(new RecipesResponse(entities))
    when: 'we subscribe'
    repository.recipes.subscribe(subscriber)

    then: 'we dont load yet'
    0 * service.recipes()
    subscriber.onNextEvents.empty

    when: 'we load'
    repository.refresh()

    then: 'we load from a service that does not immediately return'
    1 * service.recipes() >> delayedStream

    then: 'we do not receive an udpate'
    subscriber.onNextEvents.empty

    when: 'while waiting we load again'
    repository.refresh()

    then: 'we request again, with a stream that returns quickly'
    1 * service.recipes() >> secondStream

    then: 'we receive the results'
    subscriber.assertNotCompleted()
    subscriber.onNextEvents.size() == 1
    subscriber.onNextEvents.first().value == entities
    subscriber.onNextEvents.first().error == null

  }

  def 'load() - handles loads reactively'() {
    given:
    def error = new Exception("oops")
    def entities = [mockRecipe("One"), mockRecipe("Two")]
    def entities2 = [mockRecipe("Three"), mockRecipe("Four")]
    def initialValue = new RecipesResponse(entities)
    def nextValue = new RecipesResponse(entities2)
    def callOne = Observable.just(initialValue)
    def callTwo = Observable.error(error)
    def callThree = Observable.just(nextValue)
    repository.recipes.subscribe(subscriber)

    when: 'we refresh'
    repository.refresh()

    then: 'we load an initial value'
    1 * service.recipes() >> callOne

    when: 'we refresh again'
    repository.refresh()

    then: 'we get an error'
    1 * service.recipes() >> callTwo

    when: 'we try again'
    repository.refresh()

    then: 'we load more data'
    1 * service.recipes() >> callThree

    subscriber.assertNotCompleted()
    subscriber.onNextEvents.size() == 3
    subscriber.onNextEvents[0].value == entities
    subscriber.onNextEvents[0].error == null

    subscriber.onNextEvents[1].value == entities
    subscriber.onNextEvents[1].error == error

    subscriber.onNextEvents[2].value == entities2
    subscriber.onNextEvents[2].error == null


  }


  private RecipeEntity mockRecipe() {
    return new RecipeEntity("title", "link", "incredients", "image")
  }

  private RecipeEntity mockRecipe(String title) {
    return new RecipeEntity(title, "link", "incredients", "image")
  }
}