package com.github.ianellis.recipes.di.modules

//import kotlin.jvm.functions.Function0
import com.github.ianellis.recipes.di.qualifiers.MainThreadScheduler
import com.github.ianellis.recipes.domain.common.di.qualifiers.IoScheduler
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@Module
class SchedlersModle {

  @Provides
  @IoScheduler
  fun providesIoScheduler() = Schedulers.io()

  @Provides
  @MainThreadScheduler
  fun providesMainThreadScheduler() = AndroidSchedulers.mainThread()

}