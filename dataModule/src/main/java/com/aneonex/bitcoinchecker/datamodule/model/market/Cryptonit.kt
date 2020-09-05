package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Cryptonit : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyCounterLowerCase, checkerInfo.currencyBaseLowerCase) // reversed pairs!
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val rateJsonObject = jsonObject.getJSONObject("rate")
        val volumeJsonObject = jsonObject.getJSONObject("volume")
        ticker.bid = ParseUtils.getDoubleFromString(rateJsonObject, "bid")
        ticker.ask = ParseUtils.getDoubleFromString(rateJsonObject, "ask")
        if (volumeJsonObject.has(checkerInfo.currencyBase)) {
            ticker.vol = ParseUtils.getDoubleFromString(volumeJsonObject, checkerInfo.currencyBase)
        }
        ticker.high = ParseUtils.getDoubleFromString(rateJsonObject, "high")
        ticker.low = ParseUtils.getDoubleFromString(rateJsonObject, "low")
        ticker.last = ParseUtils.getDoubleFromString(rateJsonObject, "last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val pairsJsonArray = JSONArray(responseString)
        var currenciesJsonArray: JSONArray? = null
        for (i in 0 until pairsJsonArray.length()) {
            currenciesJsonArray = pairsJsonArray.getJSONArray(i)
            if (currenciesJsonArray.length() != 2) continue
            val currencyBase = currenciesJsonArray.getString(1) // reversed pairs!
            val currencyCounter = currenciesJsonArray.getString(0) // reversed pairs!
            if (currencyBase == null || currencyCounter == null) continue
            pairs.add(CurrencyPairInfo(currencyBase.toUpperCase(Locale.US), currencyCounter.toUpperCase(Locale.US), null))
        }
    }

    companion object {
        private const val NAME = "Cryptonit"
        private const val TTS_NAME = NAME
        private const val URL = "https://cryptonit.net/apiv2/rest/public/ccorder.json?bid_currency=%1\$s&ask_currency=%2\$s&ticker"
        private const val URL_CURRENCY_PAIRS = "https://cryptonit.net/apiv2/rest/public/pairs.json"
    }
}