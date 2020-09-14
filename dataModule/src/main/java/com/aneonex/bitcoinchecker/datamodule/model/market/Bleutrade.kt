package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Bleutrade : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val resultObject = jsonObject["result"]
        val resultsJsonObject = if (resultObject is JSONArray) {
            resultObject.getJSONObject(0)
        } else {
            resultObject as JSONObject
        }
        ticker.bid = resultsJsonObject!!.getDouble("Bid")
        ticker.ask = resultsJsonObject.getDouble("Ask")
        ticker.last = resultsJsonObject.getDouble("Last")
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("message")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val resultsJsonArray = jsonObject.getJSONArray("result")
        for (i in 0 until resultsJsonArray.length()) {
            val pairJsonObject = resultsJsonArray.getJSONObject(i)
            val pairId = pairJsonObject.getString("MarketName")
            val currencyBase = pairJsonObject.getString("MarketCurrency")
            val currencyCounter = pairJsonObject.getString("BaseCurrency")
            pairs.add(CurrencyPairInfo(
                    currencyBase,
                    currencyCounter,
                    pairId
            ))
        }
    }

    companion object {
        private const val NAME = "Bleutrade"
        private const val TTS_NAME = NAME
        private const val URL = "https://bleutrade.com/api/v2/public/getticker?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://bleutrade.com/api/v2/public/getmarkets"
    }
}