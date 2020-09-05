package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class CCex : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerObject = jsonObject.getJSONObject("ticker")
        ticker.bid = tickerObject.getDouble("buy")
        ticker.ask = tickerObject.getDouble("sell")
        ticker.high = tickerObject.getDouble("high")
        ticker.low = tickerObject.getDouble("low")
        ticker.last = tickerObject.getDouble("lastprice")
        //		ticker.timestamp = tickerObject.getLong("updated");	// strange date?
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairsJsonArray = jsonObject.getJSONArray("pairs")
        for (i in 0 until pairsJsonArray.length()) {
            val pair = pairsJsonArray.getString(i) ?: continue
            val currencies: Array<String?> = pair.split("-".toRegex(), 2).toTypedArray()
            if (currencies.size != 2 || currencies[0] == null || currencies[1] == null) continue
            pairs.add(CurrencyPairInfo(currencies[0]!!.toUpperCase(Locale.US), currencies[1]!!.toUpperCase(Locale.US), pair))
        }
    }

    companion object {
        const val NAME = "C-CEX"
        const val TTS_NAME = "C-Cex"
        const val URL = "https://c-cex.com/t/%1\$s-%2\$s.json"
        const val URL_CURRENCY_PAIRS = "https://c-cex.com/t/pairs.json"
    }
}