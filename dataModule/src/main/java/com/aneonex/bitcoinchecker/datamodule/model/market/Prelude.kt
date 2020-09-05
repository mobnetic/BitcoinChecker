package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*

class Prelude : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        if (requestId == 0) {
            return String.format(URL_1, checkerInfo.currencyCounterLowerCase)
        }
        return if (Currency.USD == checkerInfo.currencyCounter) {
            String.format(URL_2_USD, checkerInfo.currencyBase)
        } else {
            String.format(URL_2_BTC, checkerInfo.currencyBase)
        }
    }

    override fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 2
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        // commas?! use US number parser.
        val numberFormat = NumberFormat.getInstance(Locale.US)
        if (requestId == 0) {
            val pairings = jsonObject.getJSONArray("pairings")
            for (i in 0 until pairings.length()) {
                val pairing = pairings.getJSONObject(i)
                val pair = pairing.getString("pair")
                if (checkerInfo.currencyBase == pair) {
                    ticker.last = numberFormat.parse(pairing.getJSONObject("last_trade").getString("rate")).toDouble()
                    return
                }
            }
        } else {
            val statistics = jsonObject.getJSONObject("statistics")
            ticker.vol = numberFormat.parse(statistics.getString("volume")).toDouble()
            ticker.high = numberFormat.parse(statistics.getString("high")).toDouble()
            ticker.low = numberFormat.parse(statistics.getString("low")).toDouble()
        }
    }

    // ====================
    // Get currency pairs
    // ====================
    override val currencyPairsNumOfRequests: Int
        get() = 2

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return if (requestId == 1) URL_CURRENCY_PAIRS_USD else URL_CURRENCY_PAIRS_BTC
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairingsArray = jsonObject.getJSONArray("pairings")
        val currencyCounter = jsonObject.getString("from")
        for (i in 0 until pairingsArray.length()) {
            val pairObject = pairingsArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairObject.getString("pair"),
                    currencyCounter,
                    null
            ))
        }
    }

    companion object {
        private const val NAME = "Prelude"
        private const val TTS_NAME = NAME
        private const val URL_1 = "https://api.prelude.io/pairings/%1\$s"
        private const val URL_2_BTC = "https://api.prelude.io/statistics/%1\$s"
        private const val URL_2_USD = "https://api.prelude.io/statistics-usd/%1\$s"
        private const val URL_CURRENCY_PAIRS_BTC = "https://api.prelude.io/pairings/btc"
        private const val URL_CURRENCY_PAIRS_USD = "https://api.prelude.io/pairings/usd"
    }
}