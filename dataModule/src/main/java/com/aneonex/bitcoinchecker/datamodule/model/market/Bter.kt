package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Bter : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("highestBid")
        ticker.ask = jsonObject.getDouble("lowestAsk")
        ticker.vol = jsonObject.getDouble("quoteVolume")
        ticker.high = jsonObject.getDouble("high24hr")
        ticker.low = jsonObject.getDouble("low24hr")
        ticker.last = jsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val jsonArray = JSONArray(responseString)
        for (i in 0 until jsonArray.length()) {
            val pairId = jsonArray.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            val currencyBase = currencies[0].toUpperCase(Locale.ENGLISH)
            val currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH)
            pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, pairId))
        }
    }

    companion object {
        private const val NAME = "Gate.io"
        private const val TTS_NAME = "Gate io"
        private const val URL = "http://data.gate.io/api2/1/ticker/%1\$s_%2\$s"
        private const val URL_CURRENCY_PAIRS = "http://data.gate.io/api2/1/pairs"
    }
}