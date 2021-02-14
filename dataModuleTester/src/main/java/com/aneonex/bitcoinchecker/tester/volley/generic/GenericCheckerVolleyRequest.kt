package com.aneonex.bitcoinchecker.tester.volley.generic

import com.android.volley.Response
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.PostRequestInfo

abstract class GenericCheckerVolleyRequest<T>(url: String?, postRequestInfo: PostRequestInfo?, protected val checkerInfo: CheckerInfo, listener: Response.Listener<T>, errorListener: Response.ErrorListener)
    : GzipVolleyRequest<T>(url, postRequestInfo, listener, errorListener)