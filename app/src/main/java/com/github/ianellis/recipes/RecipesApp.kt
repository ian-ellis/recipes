package com.github.ianellis.recipes

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.github.ianellis.recipes.di.components.DaggerAppComponent

class RecipesApp : Application(), HasActivityInjector {

  @field:[Inject]
  internal lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()
    DaggerAppComponent.builder()
        .application(this)
        .build()
        .inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

}