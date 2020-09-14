package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Liqui : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if (checkerInfo.currencyPairId == null) {
            pairId = String.format("%1\$s_%2\$s", checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
        }
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val names = jsonObject.names()!!
        val tickerJsonObject = jsonObject.getJSONObject(names.getString(0))
        ticker.bid = tickerJsonObject.getDouble("sell") // REVERSED!
        ticker.ask = tickerJsonObject.getDouble("buy") // REVERSED!
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
        ticker.timestamp = tickerJsonObject.getLong("updated")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairsJsonObject = jsonObject.getJSONObject("pairs")
        val pairsNames = pairsJsonObject.names()!!
        for (i in 0 until pairsNames.length()) {
            val pairId = pairsNames.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            val currencyBase = currencies[0].toUpperCase(Locale.ENGLISH)
            val currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH)
            pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, pairId))
        }
    }

    companion object {
        //Liqui's API is similar to BTC-e, so use BTCe.java for any help with updating this code
        private const val NAME = "Liqui.io"
        private const val TTS_NAME = "Liqui"
        private const val URL = " https://api.liqui.io/api/3/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.liqui.io/api/3/info"
    }
}