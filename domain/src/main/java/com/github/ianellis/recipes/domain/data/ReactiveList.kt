package com.github.ianellis.recipes.domain.data

class ReactiveList<T> {

  private val value: List<T>
  private val error: Throwable?

  constructor(value: List<T>) {
    this.value = value
    this.error = null
  }

  constructor(error: Throwable) {
    this.value = emptyList()
    this.error = error
  }

  constructor(value: List<T>, error: Throwable?) {
    this.value = value
    this.error = error
  }

  fun getValue(): List<T> = value
  fun getError(): Throwable? = error

  fun hasValue(): Boolean = value.isNotEmpty()
  fun hasError(): Boolean = error != null
  fun noError(): Boolean = error == null

  fun <R> map(mapper: (T) -> R): ReactiveList<R> {
    val mapped: List<R> = value.map(mapper)
    return ReactiveList(mapped, error)
  }

  fun <R> process(processor: (List<T>) -> List<R>): ReactiveList<R> {
    return ReactiveList(processor.invoke(value), error)
  }
}