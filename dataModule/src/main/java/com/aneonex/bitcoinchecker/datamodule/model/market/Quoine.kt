package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Quoine : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("market_bid")
        ticker.ask = jsonObject.getDouble("market_ask")
        ticker.vol = jsonObject.getDouble("volume_24h")
        ticker.high = jsonObject.getDouble("high_market_ask")
        ticker.low = jsonObject.getDouble("low_market_bid")
        ticker.last = jsonObject.getDouble("last_traded_price")
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
        for (i in 0 until pairsJsonArray.length()) {
            val pairJsonObject = pairsJsonArray.getJSONObject(i)
            if ("CASH" != pairJsonObject.getString("code")) {
                continue
            }
            val currencyCounter = pairJsonObject.getString("currency")
            val pairName = pairJsonObject.getString("currency_pair_code")
            if (pairName.endsWith(currencyCounter)) {
                val currencyBase = pairName.substring(0, pairName.length - currencyCounter.length)
                pairs.add(CurrencyPairInfo(
                        currencyBase,
                        currencyCounter,
                        pairName))
            }
        }
    }

    companion object {
        private const val NAME = "Quoine"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.quoine.com/products/code/CASH/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.quoine.com/products/"
    }
}