package com.aneonex.bitcoinchecker.tester.util

import android.text.Spanned
import androidx.core.text.HtmlCompat

object SpannableUtils {
    fun fromHtml(source: String): Spanned {
        return HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}