package com.aneonex.bitcoinchecker.tester.volley.generic

import com.android.volley.NetworkResponse
import com.android.volley.Response

abstract class ResponseListener<T> : Response.Listener<T> {
    abstract fun onResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, response: T)
    override fun onResponse(response: T) {
        onResponse(null, null, null, null, response)
    }
}