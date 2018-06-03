package com.github.ianellis.recipes.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.github.ianellis.recipes.presentation.recipes.RecipesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(RecipesViewModel::class)
  internal abstract fun postListViewModel(viewModel: RecipesViewModel): ViewModel


}