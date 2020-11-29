package com.aneonex.bitcoinchecker.tester.volley.generic

import com.android.volley.Response
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo

abstract class GenericCheckerVolleyRequest<T>(url: String?, requestBody: String?, protected val checkerInfo: CheckerInfo, listener: Response.Listener<T>, errorListener: Response.ErrorListener)
    : GzipVolleyRequest<T>(url, requestBody, listener, errorListener)