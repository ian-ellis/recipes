package com.github.ianellis.recipes.domain.data

import rx.Observable


fun <T, R> Observable<T>.switchMapReactiveList(
    onwardCall: (T) -> Observable<List<R>>
): Observable<ReactiveList<R>> {

  return this.compose(object : Observable.Transformer<T, ReactiveList<R>> {

    private var previousValue: List<R> = emptyList()

    override fun call(observable: Observable<T>): Observable<ReactiveList<R>> {
      return observable
          .onBackpressureDrop()
          .switchMap {
            onwardCall.invoke(it)
                .doOnNext { previousValue = it }
                .map { ReactiveList(it) }
                .onErrorReturn {
                  ReactiveList(previousValue, it)
                }
          }
    }
  })
}
