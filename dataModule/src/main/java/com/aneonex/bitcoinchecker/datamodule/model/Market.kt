package com.aneonex.bitcoinchecker.datamodule.model

import android.text.TextUtils
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

abstract class Market(
        @kotlin.jvm.JvmField val name: String,
        @kotlin.jvm.JvmField val ttsName: String,
        @kotlin.jvm.JvmField val currencyPairs: CurrencyPairsMap?
    ) {

    @kotlin.jvm.JvmField
	val key: String = this.javaClass.simpleName

    open val cautionResId: Int
        get() = 0

    // ====================
    // Parse Ticker
    // ====================
    open fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 1
    }

    abstract fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String

    // When the body is set, the HTTP POST request is used
    // By default is HTTP GET
    open fun getRequestBody(requestId: Int, checkerInfo: CheckerInfo): String? {
        return null
    }

    @Throws(Exception::class)
    fun parseTickerMain(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo): Ticker {
        parseTicker(requestId, responseString, ticker, checkerInfo)
        if (ticker.timestamp <= 0) ticker.timestamp = System.currentTimeMillis() else ticker.timestamp = TimeUtils.parseTimeToMillis(ticker.timestamp)
        return ticker
    }

    @Throws(Exception::class)
    protected open fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        parseTickerFromJsonObject(requestId, JSONObject(responseString), ticker, checkerInfo)
    }

    @Throws(Exception::class)
    protected open fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        // do parsing
    }

    // ====================
    // Parse Ticker Error
    // ====================
    @Throws(Exception::class)
    fun parseErrorMain(requestId: Int, responseString: String, checkerInfo: CheckerInfo): String? {
        return parseError(requestId, responseString, checkerInfo)
    }

    @Throws(Exception::class)
    protected open fun parseError(requestId: Int, responseString: String, checkerInfo: CheckerInfo): String? {
        return parseErrorFromJsonObject(requestId, JSONObject(responseString), checkerInfo)
    }

    @Throws(Exception::class)
    protected open fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        throw Exception()
    }

    // ====================
    // Parse currency pairs
    // ====================
    open val currencyPairsNumOfRequests: Int
        get() = 1

    open fun getCurrencyPairsUrl(requestId: Int): String? {
        return null
    }

    // If body defined than used HTTP POST request
    open fun getCurrencyPairsRequestBody(requestId: Int): String? {
        return null
    }

    @Throws(Exception::class)
    fun parseCurrencyPairsMain(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        parseCurrencyPairs(requestId, responseString, pairs)
        for (i in pairs.indices.reversed()) {
            val currencyPairInfo = pairs[i]
            if (TextUtils.isEmpty(currencyPairInfo.currencyBase) || TextUtils.isEmpty(currencyPairInfo.currencyCounter)) pairs.removeAt(i)
        }
    }

    @Throws(Exception::class)
    protected open fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        parseCurrencyPairsFromJsonObject(requestId, JSONObject(responseString), pairs)
    }

    @Throws(Exception::class)
    protected open fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        // do parsing
    }
}