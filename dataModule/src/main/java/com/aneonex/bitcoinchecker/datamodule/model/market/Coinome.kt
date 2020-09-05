package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Coinome : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject(checkerInfo.currencyPairId)
        ticker.bid = tickerJsonObject.getDouble("highest_bid")
        ticker.ask = tickerJsonObject.getDouble("lowest_ask")
        //ticker.vol = tickerJsonObject.getDouble("24hr_volume"); Currently null
        ticker.last = tickerJsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairArray = jsonObject.names()
        for (i in 0 until pairArray.length()) {
            val pairId = pairArray.getString(i)
            val currencies = pairId.split("-".toRegex()).toTypedArray()
            if (currencies.size >= 2) {
                pairs.add(CurrencyPairInfo(
                        currencies[0].toUpperCase(Locale.US),
                        currencies[1].toUpperCase(Locale.US),
                        pairId
                ))
            }
        }
    }

    companion object {
        private const val NAME = "Coinome"
        private const val TTS_NAME = "Coin ome"
        private const val URL = "https://www.coinome.com/api/v1/ticker.json"
    }
}