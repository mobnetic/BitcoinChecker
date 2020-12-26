package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Bitvavo : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bitvavo"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bitvavo.com/v2/ticker/24h?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bitvavo.com/v2/markets"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)
            if(market.getString("status") == "trading") {
                pairs.add(CurrencyPairInfo(
                        market.getString("base"),
                        market.getString("quote"),
                        market.getString("market")
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.ask = getDouble("ask")
            ticker.bid = getDouble("bid")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("timestamp")
        }
    }
}