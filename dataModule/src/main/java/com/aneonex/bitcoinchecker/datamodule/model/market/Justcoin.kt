package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Justcoin : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Justcoin"
        private const val TTS_NAME = "Just coin"
        private const val URL = "https://justcoin.com/api/2/%1\$s%2\$s/money/ticker"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP,
                    Currency.HKD,
                    Currency.CHF,
                    Currency.AUD,
                    Currency.CAD,
                    Currency.NZD,
                    Currency.SGD,
                    Currency.JPY
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.DOGE] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.STR] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataObject = jsonObject.getJSONObject("data")
        ticker.bid = getPriceValueFromObject(dataObject, "buy")
        ticker.ask = getPriceValueFromObject(dataObject, "sell")
        ticker.vol = getPriceValueFromObject(dataObject, "vol")
        ticker.high = getPriceValueFromObject(dataObject, "high")
        ticker.low = getPriceValueFromObject(dataObject, "low")
        ticker.last = getPriceValueFromObject(dataObject, "last")
    }

    @Throws(Exception::class)
    private fun getPriceValueFromObject(jsonObject: JSONObject, key: String): Double {
        val innerObject = jsonObject.getJSONObject(key)
        return innerObject.getDouble("value")
    }
}