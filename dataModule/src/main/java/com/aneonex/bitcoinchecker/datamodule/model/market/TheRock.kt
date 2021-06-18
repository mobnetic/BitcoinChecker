package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class TheRock : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "TheRock"
        private const val TTS_NAME = "The Rock"
        private const val URL = "https://api.therocktrading.com/v1/funds/%1\$s/ticker"
        private const val URL_CURRENCY_PAIRS = "https://api.therocktrading.com/v1/funds"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.EUR,
                    Currency.USD,
                    VirtualCurrency.XRP
            )
            CURRENCY_PAIRS[Currency.EUR] = arrayOf(
                    VirtualCurrency.DOGE,
                    VirtualCurrency.XRP
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.NMC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.PPC] = arrayOf(
                    Currency.EUR
            )
            CURRENCY_PAIRS[Currency.USD] = arrayOf(
                    VirtualCurrency.XRP
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if (pairId == null) {
            pairId = fixCurrency(checkerInfo.currencyBase) + fixCurrency(checkerInfo.currencyCounter)
        }
        return String.format(URL, pairId)
    }

    private fun fixCurrency(currency: String?): String? {
        return if (VirtualCurrency.DOGE == currency) {
            VirtualCurrency.DOG
        } else currency
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume")
        ticker.high = jsonObject.getDouble("high")
        ticker.low = jsonObject.getDouble("low")
        ticker.last = jsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val fundsJsonArray = jsonObject.getJSONArray("funds")
        for (i in 0 until fundsJsonArray.length()) {
            val pairJsonObject = fundsJsonArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairJsonObject.getString("trade_currency"),
                    pairJsonObject.getString("base_currency"),
                    pairJsonObject.getString("id"))
            )
        }
    }
}