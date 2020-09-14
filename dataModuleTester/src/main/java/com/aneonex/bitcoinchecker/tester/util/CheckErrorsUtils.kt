package com.aneonex.bitcoinchecker.tester.util

import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import com.android.volley.*
import com.aneonex.bitcoinchecker.tester.R
import java.io.PrintWriter
import java.io.StringWriter

object CheckErrorsUtils {
    private const val RAW_RESPONSE_CHARS_LIMIT = 5000
    fun parseVolleyErrorMsg(context: Context, error: VolleyError?): String {
        return if (error is NetworkError) context.getString(R.string.check_error_network) else if (error is TimeoutError) context.getString(R.string.check_error_timeout) else if (error is ServerError) context.getString(R.string.check_error_server) else if (error is ParseError) context.getString(R.string.check_error_parse) else context.getString(R.string.check_error_unknown)
    }

    fun formatError(context: Context, errorMsg: String?): String {
        return context.getString(R.string.check_error_generic_prefix, errorMsg ?: "UNKNOWN")
    }

    private fun formatMapToHtmlString(headers: Map<String, String>): String {
        var output = ""
        for ((key, value) in headers) {
            output += String.format("<b>%1\$s</b> = %2\$s<br\\>", key, value)
        }
        return output
    }

    fun formatResponseDebug(context: Context, ssb: SpannableStringBuilder, url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, rawResponse: String?, exception: Exception?): SpannableStringBuilder {
        if (url != null) {
            ssb.append("\n\n")
            ssb.append(context.getString(R.string.ticker_raw_url, url))
        }
        if (requestHeaders != null) {
            ssb.append("\n\n")
            ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_request_headers) + "<br\\><small>" + formatMapToHtmlString(requestHeaders) + "</small>"))
        }
        if (networkResponse != null) {
            ssb.append("\n\n")
            ssb.append(context.getString(R.string.ticker_raw_response_code, networkResponse.statusCode.toString()))
            ssb.append("\n\n")
            ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_response_headers) + "<br\\><small>" + formatMapToHtmlString(networkResponse.headers) + "</small>"))
        }
        if (rawResponse != null) {
            ssb.append("\n\n")
            var limitedRawResponse: String = rawResponse
            if (rawResponse.length > RAW_RESPONSE_CHARS_LIMIT) {
                limitedRawResponse = rawResponse.substring(0, RAW_RESPONSE_CHARS_LIMIT) + "..."
            }
            ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_response) + "<br\\><small>" + limitedRawResponse + "</small>"))
        }
        if (exception != null) {
            ssb.append("\n\n")
            ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_stacktrace) + "<br\\><small>" + printException(exception) + "</small>"))
        }
        return ssb
    }

    private fun printException(e: Exception): String {
        val errors = StringWriter()
        e.printStackTrace(PrintWriter(errors))
        return errors.toString()
    }
}