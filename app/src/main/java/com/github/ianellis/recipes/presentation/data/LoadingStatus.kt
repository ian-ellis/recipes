package com.github.ianellis.recipes.presentation.data

sealed class LoadingStatus {
  class Loading : LoadingStatus()
  class Loaded : LoadingStatus()
  data class Error(val message: String?) : LoadingStatus()

}