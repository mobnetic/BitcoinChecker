package com.aneonex.bitcoinchecker.tester.volley

import android.content.Context
import android.text.TextUtils
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairsListWithDate
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.util.CurrencyPairsMapHelper
import com.aneonex.bitcoinchecker.tester.util.MarketCurrencyPairsStore
import com.aneonex.bitcoinchecker.tester.volley.generic.GzipVolleyRequest
import java.util.*

class DynamicCurrencyPairsVolleyMainRequest(private val context: Context, private val market: Market, listener: Response.Listener<CurrencyPairsMapHelper?>, errorListener: Response.ErrorListener)
    : GzipVolleyRequest<CurrencyPairsMapHelper?>(market.getCurrencyPairsUrl(0), market.getCurrencyPairsPostRequestInfo(0), listener, errorListener) {

    @Throws(Exception::class)
    override fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): CurrencyPairsMapHelper {
        val pairs: MutableList<CurrencyPairInfo> = ArrayList()
        market.parseCurrencyPairsMain(0, responseString!!, pairs)
        val numOfRequests = market.currencyPairsNumOfRequests
        if (numOfRequests > 1) {
            val nextPairs: MutableList<CurrencyPairInfo> = ArrayList()
            for (requestId in 1 until numOfRequests) {
                try {
                    val future = RequestFuture.newFuture<String>()
                    val nextUrl = market.getCurrencyPairsUrl(requestId)
                    val nextRequestInfo = market.getCurrencyPairsPostRequestInfo(requestId)
                    if (!TextUtils.isEmpty(nextUrl)) {
                        val request = DynamicCurrencyPairsVolleyNextRequest(nextUrl, nextRequestInfo, future)
                        requestQueue!!.add(request)
                        val nextResponse = future.get() // this will block
                        nextPairs.clear()
                        market.parseCurrencyPairsMain(requestId, nextResponse, nextPairs)
                        pairs.addAll(nextPairs)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        pairs.sort()
        val currencyPairsListWithDate = CurrencyPairsListWithDate()
        currencyPairsListWithDate.date = System.currentTimeMillis()
        currencyPairsListWithDate.pairs = pairs
        if (pairs.size > 0) MarketCurrencyPairsStore.savePairsForMarket(context, market.key, currencyPairsListWithDate)
        return CurrencyPairsMapHelper(currencyPairsListWithDate)
    }
}