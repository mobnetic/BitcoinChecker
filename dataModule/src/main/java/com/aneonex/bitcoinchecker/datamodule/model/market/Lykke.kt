package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class Lykke : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume24H")
        ticker.last = jsonObject.getDouble("lastPrice")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val jsonArray = JSONArray(responseString)
        for (i in 0 until jsonArray.length()) {
            val pairJsonObject = jsonArray.getJSONObject(i)

            // Parsing assets from pair in format "BTC/USD"
            val pair = pairJsonObject.getString("name").split('/')
            if(pair.size != 2)
                continue

            pairs.add(CurrencyPairInfo(
                    pair[0], // Base
                    pair[1], // Quoting
                    pairJsonObject.getString("id")))
        }
    }

    companion object {
        private const val NAME = "Lykke"
        private const val TTS_NAME = NAME
        private const val URL = "https://public-api.lykke.com/api/Market/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://public-api.lykke.com/api/AssetPairs/dictionary"
    }
}