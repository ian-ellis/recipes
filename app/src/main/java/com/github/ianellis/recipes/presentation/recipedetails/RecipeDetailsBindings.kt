package com.github.ianellis.recipes.presentation.recipedetails

import android.databinding.BindingAdapter
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.URLSpan
import android.text.util.Linkify
import android.widget.TextView
import com.github.ianellis.recipes.presentation.utils.UrlSpanHandler

object RecipeDetailsBindings {

  @JvmStatic
  @BindingAdapter("ingredients")
  fun bindIngredients(ingredientsList: IngredientsList, ingredients: List<String>) {
    ingredientsList.ingredients = ingredients
  }

  @JvmStatic
  @BindingAdapter("autolinkHandler")
  fun bindAutoLinkAndHandler(textView: TextView, handler: (String) -> Unit) {
    Linkify.addLinks(textView, Linkify.WEB_URLS)
    val spans = textView.text as? SpannableString
    if (spans != null) {

      val urlSpans = spans.getSpans(0, spans.length, URLSpan::class.java)
      val customLinked= SpannableStringBuilder(textView.text.toString())
      customLinked.clearSpans()

      for (span in urlSpans) {
        val start = spans.getSpanStart(span)
        val end = spans.getSpanEnd(span)
        customLinked.setSpan(UrlSpanHandler(span.url, handler), start, end, 0)
      }

      textView.text = customLinked
    }
  }

}