package com.github.ianellis.recipes.presentation.utils

import android.text.style.URLSpan
import android.view.View

class UrlSpanHandler(url: String, private val handler: (String) -> Unit) : URLSpan(url) {

  override fun onClick(widget: View) {
    handler(url)
  }
}