package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Liquid : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Liquid"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.liquid.com/products/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.liquid.com/products"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = JSONArray(responseString)
        for (i in 0 until marketsJson.length()) {
            val market = marketsJson.getJSONObject(i)

            pairs.add(
                CurrencyPairInfo(
                        market.getString("base_currency"),
                        market.getString("quoted_currency"),
                        market.getString("id")
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.bid = getDouble("market_bid")
            ticker.ask = getDouble("market_ask")
            ticker.high = getDouble("high_market_ask")
            ticker.low = getDouble("low_market_bid")
            ticker.last = getDouble("last_traded_price")
            ticker.vol = getDouble("volume_24h")

            // Convert "1603044568.009619132" to "1603044568"
            ticker.timestamp = (getString("last_event_timestamp").split('.')[0]).toLong()
        }
    }
}