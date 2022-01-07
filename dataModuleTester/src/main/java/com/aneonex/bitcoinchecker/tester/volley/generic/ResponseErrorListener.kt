package com.aneonex.bitcoinchecker.tester.volley.generic

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError

abstract class ResponseErrorListener : Response.ErrorListener {
    abstract fun onErrorResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, error: VolleyError)
    override fun onErrorResponse(error: VolleyError) {
        onErrorResponse(null, null, null, null, error)
    }
}