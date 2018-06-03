package com.github.ianellis.recipes.presentation.webview

import android.content.Context
import android.content.Intent

object WebViewLauncher {

  private const val URL_KEY = "url"
  private const val URL_TITLE = "title"

  fun launch(from: Context, withUrl: String, andTitle:String): Intent = Intent(from, SimpleWebViewActivity::class.java).apply {
    putExtra(URL_KEY, withUrl)
    putExtra(URL_TITLE, andTitle)
  }

  internal fun deserialize(from: Intent): Pair<String,String> {
    return Pair(from.getStringExtra(URL_KEY),from.getStringExtra(URL_TITLE))
  }

}