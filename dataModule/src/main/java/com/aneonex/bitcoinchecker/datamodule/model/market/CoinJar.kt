package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class CoinJar : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val ratesJsonObject = jsonObject.getJSONObject("exchange_rates")
        val pairJsonObject = ratesJsonObject.getJSONObject(checkerInfo.currencyPairId!!)
        ticker.bid = pairJsonObject.getDouble("bid")
        ticker.ask = pairJsonObject.getDouble("ask")
        ticker.last = pairJsonObject.getDouble("midpoint")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val ratesJsonObject = jsonObject.getJSONObject("exchange_rates")
        val namesJsonArray = ratesJsonObject.names()!!
        for (i in 0 until namesJsonArray.length()) {
            val symbol = namesJsonArray.getString(i)
            val pairJsonObject = ratesJsonObject.getJSONObject(symbol)
            pairs.add(CurrencyPairInfo(
                    pairJsonObject.getString("base_currency"),
                    pairJsonObject.getString("counter_currency"),
                    symbol
            ))
        }
    }

    companion object {
        private const val NAME = "CoinJar Prices"
        private const val TTS_NAME = "Coin Jar prices"
        private const val URL = "https://api.coinjar.com/v3/exchange_rates"
        private const val URL_CURRENCY_PAIRS = URL
    }
}