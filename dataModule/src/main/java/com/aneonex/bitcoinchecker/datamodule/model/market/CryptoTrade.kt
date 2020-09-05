package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class CryptoTrade : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Crypto-Trade"
        private const val TTS_NAME = "Crypto Trade"
        private const val URL = "https://crypto-trade.com/api/1/ticker/%1\$s_%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.NMC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.XPM] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.BTC,
                    VirtualCurrency.PPC
            )
            CURRENCY_PAIRS[VirtualCurrency.PPC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.TRC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.FTC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.DVC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.WDC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.DGC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.UTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataObject = jsonObject.getJSONObject("data")
        ticker.bid = dataObject.getDouble("max_bid")
        ticker.ask = dataObject.getDouble("min_ask")
        ticker.vol = dataObject.getDouble("vol_" + checkerInfo.currencyBaseLowerCase)
        ticker.high = dataObject.getDouble("high")
        ticker.low = dataObject.getDouble("low")
        ticker.last = dataObject.getDouble("last")
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("error")
    }
}