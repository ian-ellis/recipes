package com.github.ianellis.recipes.domain.data

import kotlin.jvm.functions.Function1
import rx.observers.TestSubscriber
import rx.subjects.BehaviorSubject
import spock.lang.Specification
import rx.Observable

class ReactiveOperatorsSpec extends Specification {

  def 'switchMapReactiveList switchMaps on input stream and converts results to Reactivelist'(){
    given:
    def subscriber = new TestSubscriber<ReactiveList<String>>()
    def inputValue = "inputevent"
    def upStreamValue = ["a","b","C"]
    def onwardCall = Mock(Function1)

    def input = BehaviorSubject.create()
    ReactiveOperatorsKt.switchMapReactiveList(input,onwardCall).subscribe(subscriber)

    when:
    input.onNext(inputValue)
    then:
    1 * onwardCall.invoke(inputValue) >> Observable.just(upStreamValue)
    subscriber.onNextEvents.size() == 1
    subscriber.onNextEvents.first().value == upStreamValue
    subscriber.onNextEvents.first().error == null
  }

  def 'switchMapReactiveList switchMaps on input stream and passes on errors'(){
    given:
    def subscriber = new TestSubscriber<ReactiveList<String>>()
    def inputValue = "inputevent"
    def error = new Exception("oops")
    def onwardCall = Mock(Function1)

    def input = BehaviorSubject.create()
    ReactiveOperatorsKt.switchMapReactiveList(input,onwardCall).subscribe(subscriber)

    when:
    input.onNext(inputValue)

    then:
    1 * onwardCall.invoke(inputValue) >> Observable.error(error)
    subscriber.onNextEvents.size() == 1
    subscriber.onNextEvents.first().value == []
    subscriber.onNextEvents.first().error == error
  }

  def 'switchMapReactiveList switchMaps on input stream and provides previous value with passes errors'(){
    given:
    def subscriber = new TestSubscriber<ReactiveList<String>>()
    def inputValue = "inputevent"
    def upStreamValue = ["a","b","C"]
    def error = new Exception("oops")
    def onwardCall = Mock(Function1)

    def input = BehaviorSubject.create()
    ReactiveOperatorsKt.switchMapReactiveList(input,onwardCall).subscribe(subscriber)

    when:
    input.onNext(inputValue)

    then:
    1 * onwardCall.invoke(inputValue) >> Observable.just(upStreamValue)

    when:
    input.onNext(inputValue)

    then:
    1 * onwardCall.invoke(inputValue) >> Observable.error(error)

    subscriber.onNextEvents.size() == 2
    subscriber.onNextEvents.first().value == upStreamValue
    subscriber.onNextEvents.first().error == null

    subscriber.onNextEvents.last().value == upStreamValue
    subscriber.onNextEvents.last().error == error
  }

}