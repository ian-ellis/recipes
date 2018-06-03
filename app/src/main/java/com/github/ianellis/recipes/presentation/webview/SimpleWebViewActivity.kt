package com.github.ianellis.recipes.presentation.webview

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.ianellis.recipes.R
import com.github.ianellis.recipes.presentation.BaseActivity


class SimpleWebViewActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val cookieManager = CookieManager.getInstance()
    cookieManager.removeAllCookies(null)

    setContentView(R.layout.activity_simple_webview)
    val webview = findViewById<WebView>(R.id.view_webview)
    webview.settings.javaScriptEnabled = true
    webview.webViewClient = WebViewClient()
    val (url,title) = WebViewLauncher.deserialize(from = intent)
    webview.loadUrl(url)
    supportActionBar?.title = title
  }

  override fun onBackPressed() {
    super.onBackPressed()
    overridePendingTransition(R.anim.no_move, R.anim.slide_out_down)
  }
}