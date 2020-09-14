package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Cryptoine : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = getDoubleOrZero(jsonObject, "buy")
        ticker.ask = getDoubleOrZero(jsonObject, "sell")
        ticker.vol = getDoubleOrZero(jsonObject, "vol_exchange")
        ticker.high = getDoubleOrZero(jsonObject, "high")
        ticker.low = getDoubleOrZero(jsonObject, "low")
        ticker.last = getDoubleOrZero(jsonObject, "last")
    }

    @Throws(Exception::class)
    private fun getDoubleOrZero(jsonObject: JSONObject, name: String): Double {
        return if (jsonObject.isNull(name)) 0.0 else jsonObject.getDouble(name)
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonObject = jsonObject.getJSONObject("data")
        val pairNames = dataJsonObject.names()!!
        for (i in 0 until pairNames.length()) {
            val pairId = pairNames.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            val currencyBase = currencies[0].toUpperCase(Locale.ENGLISH)
            val currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH)
            pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, pairId))
        }
    }

    companion object {
        private const val NAME = "Cryptoine"
        private const val TTS_NAME = NAME
        private const val URL = "https://cryptoine.com/api/1/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://cryptoine.com/api/1/markets"
    }
}