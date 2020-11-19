package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Hbtc : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "HBTC"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.hbtc.com/openapi/quote/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.hbtc.com/openapi/v1/pairs"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            pairs.add( CurrencyPairInfo(
                    market.getString("baseToken"),
                    market.getString("quoteToken"),
                    market.getString("symbol")
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.ask = getDouble("bestAskPrice")
            ticker.bid = getDouble("bestBidPrice")
            ticker.high = getDouble("highPrice")
            ticker.low = getDouble("lowPrice")
            ticker.last = getDouble("lastPrice")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}