package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class CexIO : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "CEX.IO"
        private const val TTS_NAME = "CEX IO"
        private const val URL = "https://cex.io/api/ticker/%1\$s/%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://cex.io/api/currency_limits"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP,
                    Currency.RUB)
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.GHS] = arrayOf(
                    VirtualCurrency.BTC)
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        if (jsonObject.has("bid")) {
            ticker.bid = jsonObject.getDouble("bid")
        }
        if (jsonObject.has("ask")) {
            ticker.ask = jsonObject.getDouble("ask")
        }
        ticker.vol = jsonObject.getDouble("volume")
        ticker.high = jsonObject.getDouble("high")
        ticker.low = jsonObject.getDouble("low")
        ticker.last = jsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonObject = jsonObject.getJSONObject("data")
        val pairsJsonArray = dataJsonObject.getJSONArray("pairs")
        for (i in 0 until pairsJsonArray.length()) {
            val pairJsonObject = pairsJsonArray.getJSONObject(i)
            val currencyBase = pairJsonObject.getString("symbol1")
            val currencyCounter = pairJsonObject.getString("symbol2")
            if (currencyBase != null && currencyCounter != null) {
                pairs.add(CurrencyPairInfo(
                        currencyBase,
                        currencyCounter,
                        null))
            }
        }
    }
}