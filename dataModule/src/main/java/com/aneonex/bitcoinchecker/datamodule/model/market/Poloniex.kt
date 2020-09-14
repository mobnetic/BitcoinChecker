package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Poloniex : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairJsonObject = jsonObject.getJSONObject(checkerInfo.currencyCounter + "_" + checkerInfo.currencyBase) // Reversed currencies
        ticker.bid = pairJsonObject.getDouble("highestBid")
        ticker.ask = pairJsonObject.getDouble("lowestAsk")
        ticker.vol = pairJsonObject.getDouble("baseVolume")
        ticker.last = pairJsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairNames = jsonObject.names()!!
        for (i in 0 until pairNames.length()) {
            val pairId = pairNames.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(CurrencyPairInfo(currencies[1], currencies[0], pairId)) //reversed pairs
        }
    }

    companion object {
        private const val NAME = "Poloniex"
        private const val TTS_NAME = NAME
        private const val URL = "https://poloniex.com/public?command=returnTicker"
    }
}