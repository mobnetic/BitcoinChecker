package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Kucoin : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("data")
        ticker.bid = tickerJsonObject.getDouble("buy")
        ticker.ask = tickerJsonObject.getDouble("sell")
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
        ticker.timestamp = tickerJsonObject.getLong("time")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_COINS_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val data = jsonObject.getJSONArray("data")
        for (i in 0 until data.length()) {
            val pairJsonObject = data.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairJsonObject.getString("baseCurrency"),
                    pairJsonObject.getString("quoteCurrency"),
                    pairJsonObject.getString("symbol")
            ))
        }
    }

    companion object {
        private const val NAME = "Kucoin"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.kucoin.com/api/v1/market/stats?symbol=%1\$s"
        private const val URL_COINS_PAIRS = "https://api.kucoin.com/api/v1/symbols"
    }
}