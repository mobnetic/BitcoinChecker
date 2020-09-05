package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject
import java.util.*

class Igot : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairJsonObject = jsonObject.getJSONObject(checkerInfo.currencyPairId)
        ticker.high = pairJsonObject.getDouble("highest_today")
        ticker.low = pairJsonObject.getDouble("lowest_today")
        ticker.last = pairJsonObject.getDouble("current_rate")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairsJsonArray = jsonObject.names()
        for (i in 0 until pairsJsonArray.length()) {
            val currencyCounter = pairsJsonArray.getString(i)
            if (currencyCounter != null) {
                pairs.add(CurrencyPairInfo(
                        VirtualCurrency.BTC,
                        currencyCounter.toUpperCase(Locale.ENGLISH),
                        currencyCounter))
            }
        }
    }

    companion object {
        private const val NAME = "igot"
        private const val TTS_NAME = "igot"
        private const val URL = "https://www.igot.com/api/v1/stats/buy"
        private const val URL_CURRENCY_PAIRS = URL
    }
}