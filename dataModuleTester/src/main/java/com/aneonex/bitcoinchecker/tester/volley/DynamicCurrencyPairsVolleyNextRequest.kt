package com.aneonex.bitcoinchecker.tester.volley

import com.android.volley.toolbox.RequestFuture
import com.aneonex.bitcoinchecker.datamodule.model.PostRequestInfo
import com.aneonex.bitcoinchecker.tester.volley.generic.GzipVolleyRequest

class DynamicCurrencyPairsVolleyNextRequest(url: String?, postRequestInfo: PostRequestInfo?, future: RequestFuture<String>) : GzipVolleyRequest<String?>(url, postRequestInfo, future, future) {
    @Throws(Exception::class)
    override fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): String? {
        return responseString
    }
}