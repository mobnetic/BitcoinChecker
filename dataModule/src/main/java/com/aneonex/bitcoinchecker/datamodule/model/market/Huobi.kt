package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Huobi : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("tick")
        ticker.bid = tickerJsonObject.getJSONArray("bid").getDouble(0)
        ticker.ask = tickerJsonObject.getJSONArray("ask").getDouble(0)
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("close")
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        if ("ok".equals(jsonObject.getString("status"), ignoreCase = true)) {
            val data = jsonObject.getJSONArray("data")
            for (i in 0 until data.length()) {
                val base = data.getJSONObject(i).getString("base-currency").toUpperCase(Locale.US)
                val counter = data.getJSONObject(i).getString("quote-currency").toUpperCase(Locale.US)
                pairs.add(CurrencyPairInfo(base, counter, null))
            }
        } else {
            throw Exception("Parse currency pairs error.")
        }
    }

    companion object {
        private const val NAME = "Huobi"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.huobi.pro/market/detail/merged?symbol=%s%s"
        private const val URL_CURRENCY_PAIRS = "https://api.huobi.pro/v1/common/symbols"
    }
}