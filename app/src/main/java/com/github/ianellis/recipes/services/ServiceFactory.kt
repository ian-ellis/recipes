package com.github.ianellis.recipes.services

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ServiceFactory constructor(
    private val gson: Gson,
    private val httpClient: OkHttpClient
) {

  fun <T> createService(clazz: Class<T>, endpoint: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .client(httpClient)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(clazz)
  }
}