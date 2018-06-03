package com.github.ianellis.recipes.di.components

import android.app.Application
import com.github.ianellis.recipes.RecipesApp
import com.github.ianellis.recipes.di.buildermodules.ActivityBuilder
import com.github.ianellis.recipes.di.modules.SchedlersModle
import com.github.ianellis.recipes.di.modules.ServicesModule
import com.github.ianellis.recipes.di.modules.SystemServicesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidInjectionModule::class,
  AndroidSupportInjectionModule::class,
  ActivityBuilder::class,
  SystemServicesModule::class,
  ServicesModule::class,
  SchedlersModle::class
])
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: RecipesApp)
}
