package com.github.ianellis.recipes.domain.data

import kotlin.jvm.functions.Function1
import spock.lang.Specification
import spock.lang.Unroll

class ReactiveListSpec extends Specification {

  def 'ReactiveList(value) sets value to provided param and  error to null'() {
    given:
    def originalList = [1, 2, 3, 4, 5]
    when:
    def reactiveList = new ReactiveList(originalList)
    then:
    reactiveList.value == originalList
    reactiveList.error == null
  }

  def 'ReactiveList(error) sets value empty list and error to provided error'() {
    given:
    def originalError = new Exception("oops")
    when:
    def reactiveList = new ReactiveList(originalError)
    then:
    reactiveList.value == []
    reactiveList.error == originalError
  }

  def 'ReactiveList(value, error) sets value provided list and error to provided error'() {
    given:
    def originalList = [1, 2, 3, 4, 5]
    def originalError = new Exception("oops")
    when:
    def reactiveList = new ReactiveList(originalList, originalError)
    then:
    reactiveList.value == originalList
    reactiveList.error == originalError
  }

  @Unroll
  def 'hasValue() returns #expected when #when'() {
    given:
    def reactiveList = new ReactiveList<String>(value)

    when:
    def result = reactiveList.hasValue()

    then:
    result == expected

    where:
    value | expected | when
    ["1"] | true     | "value is not empty"
    []    | false    | "value is empty"

  }

  @Unroll
  def 'hasError() returns #expected when #when'() {
    given:
    def reactiveList = new ReactiveList<String>([], error)

    when:
    def result = reactiveList.hasError()

    then:
    result == expected

    where:
    error           | expected | when
    new Exception() | true     | "error is not null"
    null            | false    | "error is null"

  }

  @Unroll
  def 'noError() returns #expected when #when'() {
    given:
    def reactiveList = new ReactiveList<String>([], error)

    when:
    def result = reactiveList.noError()

    then:
    result == expected

    where:
    error           | expected | when
    new Exception() | false    | "error is not null"
    null            | true     | "error is null"

  }

  def 'map() maps data and returns new list with mapped content and original error'() {
    given:
    def originalList = [1, 2, 3, 4, 5]
    def originalError = new RuntimeException("oops")
    def reactiveList = new ReactiveList(originalList, originalError)
    when:
    def mapped = reactiveList.map(new Function1<Integer, String>() {
      @Override
      String invoke(Integer value) {
        return "$value"
      }
    })
    then:
    mapped.error == originalError
    mapped.value.size() == 5
    mapped.value[0] == "1"
    mapped.value[1] == "2"
    mapped.value[2] == "3"
    mapped.value[3] == "4"
    mapped.value[4] == "5"
  }

  def 'process() runs function over whole list and returns processed value with original error'() {
    given:
    def originalList = [1, 2, 3, 4, 5]
    def originalError = new RuntimeException("oops")
    def reactiveList = new ReactiveList(originalList, originalError)
    when:
    def mapped = reactiveList.process(new Function1<List<Integer>, List<String>>() {
      @Override
      List<String> invoke(List<Integer> value) {
        return ['1', '2', '3']
      }
    })
    then:
    mapped.error == originalError
    mapped.value.size() == 3
    mapped.value[0] == "1"
    mapped.value[1] == "2"
    mapped.value[2] == "3"
  }
}