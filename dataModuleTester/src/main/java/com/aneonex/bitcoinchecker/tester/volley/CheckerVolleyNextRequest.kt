package com.aneonex.bitcoinchecker.tester.volley

import com.android.volley.toolbox.RequestFuture
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.tester.volley.generic.GenericCheckerVolleyRequest

class CheckerVolleyNextRequest(url: String?, checkerInfo: CheckerInfo, future: RequestFuture<String>) : GenericCheckerVolleyRequest<String?>(url, checkerInfo, future, future) {
    @Throws(Exception::class)
    override fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): String? {
        return responseString
    }
}