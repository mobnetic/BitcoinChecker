package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Exmo : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairJsonObject = jsonObject.getJSONObject(checkerInfo.currencyBase + "_" + checkerInfo.currencyCounter)
        ticker.bid = pairJsonObject.getDouble("buy_price")
        ticker.ask = pairJsonObject.getDouble("sell_price")
        ticker.vol = pairJsonObject.getDouble("vol")
        ticker.high = pairJsonObject.getDouble("high")
        ticker.low = pairJsonObject.getDouble("low")
        ticker.last = pairJsonObject.getDouble("last_trade")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairIds = jsonObject.names()!!
        for (i in 0 until pairIds.length()) {
            val pairId = pairIds.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(CurrencyPairInfo(currencies[0], currencies[1], pairId))
        }
    }

    companion object {
        private const val NAME = "Exmo"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.exmo.com/v1/ticker/"
        private const val URL_CURRENCY_PAIRS = "https://api.exmo.com/v1/pair_settings/"
    }
}