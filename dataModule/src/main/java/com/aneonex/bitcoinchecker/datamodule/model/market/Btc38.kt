package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject
import java.util.*

class Btc38 : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = tickerJsonObject.getDouble("buy")
        ticker.ask = tickerJsonObject.getDouble("sell")
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override val currencyPairsNumOfRequests: Int
        get() = 2

    private fun getCurrencyCounter(requestId: Int): String {
        return if (requestId == 0) Currency.CNY else VirtualCurrency.BTC
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return String.format(URL_CURRENCY_PAIRS, getCurrencyCounter(requestId).toLowerCase(Locale.ENGLISH))
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val currencyCounter = getCurrencyCounter(requestId)
        val currencyBaseList = jsonObject.names()
        for (i in 0 until currencyBaseList.length()) {
            pairs.add(CurrencyPairInfo(
                    currencyBaseList.getString(i).toUpperCase(Locale.ENGLISH),
                    currencyCounter,
                    null
            ))
        }
    }

    companion object {
        private const val NAME = "Btc38"
        private const val TTS_NAME = "BTC 38"
        private const val URL = "http://api.btc38.com/v1/ticker.php?c=%1\$s&mk_type=%2\$s"
        private const val URL_CURRENCY_PAIRS = "http://api.btc38.com/v1/ticker.php?c=all&mk_type=%1\$s"
    }
}