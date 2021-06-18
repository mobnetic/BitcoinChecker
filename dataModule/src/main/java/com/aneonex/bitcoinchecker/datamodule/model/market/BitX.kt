package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class BitX : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Luno"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.mybitx.com/api/1/ticker?pair=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.mybitx.com/api/1/tickers"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.IDR,
                    Currency.SGD,
                    Currency.MYR,
                    Currency.NGN,
                    Currency.ZAR
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val pairString: String?
        pairString = checkerInfo.currencyPairId
                ?: String.format("%1\$s%2\$s", fixCurrency(checkerInfo.currencyBase), fixCurrency(checkerInfo.currencyCounter))
        return String.format(URL, pairString)
    }

    private fun fixCurrency(currency: String): String {
        if (VirtualCurrency.BTC == currency) {
            return VirtualCurrency.XBT
        } else if (VirtualCurrency.XBT == currency) {
            return VirtualCurrency.BTC
        }
        return currency
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("rolling_24_hour_volume")
        ticker.last = jsonObject.getDouble("last_trade")
        ticker.timestamp = jsonObject.getLong("timestamp")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonArray = jsonObject.getJSONArray("tickers")
        for (i in 0 until dataJsonArray.length()) {
            val currencyPair = dataJsonArray.getJSONObject(i).getString("pair") ?: continue
            var currencyBase: String
            var currencyCounter: String
            try {
                currencyBase = fixCurrency(currencyPair.substring(0, 3))
                currencyCounter = fixCurrency(currencyPair.substring(3))
            } catch (e: Exception) {
                continue
            }
            pairs.add(CurrencyPairInfo(
                    currencyBase,
                    currencyCounter,
                    currencyPair
            ))
        }
    }
}