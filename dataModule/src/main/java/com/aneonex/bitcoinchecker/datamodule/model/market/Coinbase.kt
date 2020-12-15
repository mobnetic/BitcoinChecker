package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray
import org.json.JSONObject

class Coinbase : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Coinbase Pro"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://api.pro.coinbase.com/products/%1\$s/ticker"
        private const val URL_STATS = "https://api.pro.coinbase.com/products/%1\$s/stats"
        private const val URL_CURRENCY_PAIRS = "https://api.pro.coinbase.com/products"
    }

    override fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 2
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        // Compatibility with old Coinbase implementation (before v2.19)
        val pairId = checkerInfo.currencyPairId ?: "${checkerInfo.currencyBase}-${checkerInfo.currencyCounter}"

        if(requestId == 0)
            return String.format(URL_TICKER, pairId)

        return String.format(URL_STATS, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        if(requestId == 0) {
            ticker.bid = jsonObject.getDouble("bid")
            ticker.ask = jsonObject.getDouble("ask")
            ticker.vol = jsonObject.getDouble("volume")
            ticker.last = jsonObject.getDouble("price")
            ticker.timestamp = TimeUtils.convertISODateToTimestamp(jsonObject.getString("time"))
        }
        else {
            ticker.high = jsonObject.getDouble("high")
            ticker.low = jsonObject.getDouble("low")
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val jsonArray = JSONArray(responseString)
        for (i in 0 until jsonArray.length()) {
            val pairJsonObject = jsonArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairJsonObject.getString("base_currency"),
                    pairJsonObject.getString("quote_currency"),
                    pairJsonObject.getString("id")))
        }
    }
}