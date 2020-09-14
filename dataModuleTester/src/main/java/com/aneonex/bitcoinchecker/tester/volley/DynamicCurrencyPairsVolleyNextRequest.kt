package com.aneonex.bitcoinchecker.tester.volley

import com.android.volley.toolbox.RequestFuture
import com.aneonex.bitcoinchecker.tester.volley.generic.GzipVolleyRequest

class DynamicCurrencyPairsVolleyNextRequest(url: String?, future: RequestFuture<String>) : GzipVolleyRequest<String?>(url, future, future) {
    @Throws(Exception::class)
    override fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): String? {
        return responseString
    }
}