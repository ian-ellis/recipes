package com.github.ianellis.recipes.di.modules

import android.app.Application
import android.content.res.Resources
import com.github.ianellis.recipes.services.ServiceFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class SystemServicesModule {

  companion object {
    private const val READ_TIMEOUT_SECONDS = 5L
    private const val CONNECTION_TIMEOUT_SECONDS = 5L
    private const val WRITE_TIMEOUT_SECONDS = 5L
  }

  @Provides
  @Singleton
  fun providesGson(): Gson {
    return GsonBuilder().create()
  }

  @Provides
  @Singleton
  fun providesOkHttp(): OkHttpClient {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()
  }

  @Provides
  fun providesServiceFactory(gson: Gson, okHttp: OkHttpClient): ServiceFactory {
    return ServiceFactory(gson, okHttp)
  }

  @Provides
  fun providesResources(app: Application): Resources = app.resources
}