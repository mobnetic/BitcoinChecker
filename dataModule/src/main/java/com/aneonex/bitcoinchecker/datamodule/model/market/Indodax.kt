package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Indodax : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Indodax"
        private const val TTS_NAME = NAME
        private const val URL = "https://indodax.com/api/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://indodax.com/api/pairs"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = JSONArray(responseString)
        for (i in 0 until marketsJson.length()) {
            val market = marketsJson.getJSONObject(i)

            pairs.add(
                CurrencyPairInfo(
                    market.getString("traded_currency").uppercase(Locale.ROOT), // Base currency
                    market.getString("base_currency")
                        .uppercase(Locale.ROOT), // base_currency is real quoting
                    market.getString("id")
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("ticker").apply {
            ticker.bid = getDouble("buy")
            ticker.ask = getDouble("sell")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.timestamp = getLong("server_time")

            ticker.vol = getDouble("vol_${checkerInfo.currencyBaseLowerCase}")
        }
    }
}