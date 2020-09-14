package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Braziliex : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("highestBid")
        ticker.ask = jsonObject.getDouble("lowestAsk")
        ticker.vol = jsonObject.getDouble("baseVolume24")
        ticker.high = jsonObject.getDouble("highestBid24")
        ticker.low = jsonObject.getDouble("lowestAsk24")
        ticker.last = jsonObject.getDouble("last")
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
            val currencyBase = currencies[0].toUpperCase(Locale.ENGLISH)
            val currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH)
            pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, pairId))
        }
    }

    companion object {
        private const val NAME = "Braziliex"
        private const val TTS_NAME = NAME
        private const val URL = "https://braziliex.com/api/v1/public/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://braziliex.com/api/v1/public/ticker"
    }
}