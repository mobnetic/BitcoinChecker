package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachString
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Gemini : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Gemini"
        private const val TTS_NAME = "Gemini"
        private const val URL = "https://api.gemini.com/v1/pubticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.gemini.com/v1/symbols"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val quoteCurrencyLength = 3

        JSONArray(responseString).forEachString { market ->
            pairs.add(
                CurrencyPairInfo(
                    market.substring(0, market.length - quoteCurrencyLength).uppercase(Locale.ROOT),
                    market.substring(market.length - quoteCurrencyLength).uppercase(Locale.ROOT),
                    market
                ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        // Compatibility with old pre-installed pairs
        val pairId = checkerInfo.currencyPairId ?: (checkerInfo.currencyBase + checkerInfo.currencyCounter)
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.last = jsonObject.getDouble("last")
        jsonObject.getJSONObject("volume").apply {
            ticker.vol = getDouble(checkerInfo.currencyBase)

            // The end of the 24-hour period over which volume was measured
            // This is not the last price time
            // ticker.timestamp = getLong("timestamp")
        }
    }
}