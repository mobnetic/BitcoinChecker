package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Livecoin : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        if (!jsonObject.isNull("best_bid")) {
            ticker.bid = jsonObject.getDouble("best_bid")
        }
        if (!jsonObject.isNull("best_ask")) {
            ticker.ask = jsonObject.getDouble("best_ask")
        }
        if (!jsonObject.isNull("volume")) {
            ticker.vol = jsonObject.getDouble("volume")
        }
        if (!jsonObject.isNull("high")) {
            ticker.high = jsonObject.getDouble("high")
        }
        if (!jsonObject.isNull("low")) {
            ticker.low = jsonObject.getDouble("low")
        }
        ticker.last = jsonObject.getDouble("last")
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val tickerArray = JSONArray(responseString)
        for (i in 0 until tickerArray.length()) {
            val tickerRow = tickerArray.getJSONObject(i)
            val symbol = tickerRow.getString("symbol")
            val currencyNames = symbol.split("/".toRegex()).toTypedArray()
            if (currencyNames.size >= 2) {
                pairs.add(CurrencyPairInfo(
                        currencyNames[0],
                        currencyNames[1],
                        symbol))
            }
        }
    }

    companion object {
        private const val NAME = "Livecoin"
        private const val TTS_NAME = "Live coin"
        private const val URL = "https://api.livecoin.net/exchange/ticker?currencyPair=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.livecoin.net/exchange/ticker"
    }
}