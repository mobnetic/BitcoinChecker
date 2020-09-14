package com.aneonex.bitcoinchecker.tester.volley

import android.text.TextUtils
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.tester.volley.CheckerVolleyMainRequest.TickerWrapper
import com.aneonex.bitcoinchecker.tester.volley.generic.GenericCheckerVolleyRequest

class CheckerVolleyMainRequest(market: Market, checkerInfo: CheckerInfo, listener: Response.Listener<TickerWrapper?>, errorListener: Response.ErrorListener)
    : GenericCheckerVolleyRequest<TickerWrapper?>(market.getUrl(0, checkerInfo), checkerInfo, listener, errorListener) {

    private val market: Market
    @Throws(Exception::class)
    override fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): TickerWrapper? {
        val tickerWrapper = TickerWrapper()
        try {
            tickerWrapper.ticker = market.parseTickerMain(0, responseString!!, Ticker(), checkerInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            tickerWrapper.ticker = null
        }
        if (tickerWrapper.ticker == null || tickerWrapper.ticker!!.last <= Ticker.NO_DATA) {
            val errorMsg: String? = try {
                market.parseErrorMain(0, responseString!!, checkerInfo)
            } catch (e: Exception) {
                null
            }
            throw CheckerErrorParsedError(errorMsg)
        }

        val numOfRequests = market.getNumOfRequests(checkerInfo)
        if (numOfRequests > 1) {
            for (requestId in 1 until numOfRequests) {
                try {
                    val future = RequestFuture.newFuture<String>()
                    val nextUrl = market.getUrl(requestId, checkerInfo)
                    if (!TextUtils.isEmpty(nextUrl)) {
                        val request = CheckerVolleyNextRequest(nextUrl, checkerInfo, future)
                        requestQueue!!.add(request)
                        val nextResponse = future.get() // this will block
                        market.parseTickerMain(requestId, nextResponse, tickerWrapper.ticker!!, checkerInfo)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return tickerWrapper
    }

    inner class TickerWrapper {
        var ticker: Ticker? = null
    }

    init {
        retryPolicy = DefaultRetryPolicy(5000, 3, 1.5f)
        this.market = market
    }
}