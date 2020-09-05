package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class OmniTrade : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = dataJsonObject.getDouble("buy")
        ticker.ask = dataJsonObject.getDouble("sell")
        ticker.low = dataJsonObject.getDouble("low")
        ticker.high = dataJsonObject.getDouble("high")
        ticker.last = dataJsonObject.getDouble("last")
        ticker.vol = dataJsonObject.getDouble("vol")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val pairsArray = JSONArray(responseString)
        for (i in 0 until pairsArray.length()) {
            val pair = pairsArray.getJSONObject(i)
            val currencies = pair.getString("name").split("/".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(CurrencyPairInfo(currencies[0], currencies[1], pair.getString("id")))
        }
    }

    companion object {
        private const val NAME = "OmniTrade"
        private const val TTS_NAME = "Omni Trade"
        private const val URL = "https://omnitrade.io/api/v2/tickers/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://omnitrade.io/api/v2/markets"
    }
}