package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class SatoshiTango : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "SatoshiTango"
        private const val TTS_NAME = "Satoshi Tango"
        private const val URL = "https://api.satoshitango.com/v2/ticker"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.ARS,
                    Currency.EUR
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val currencyCode = checkerInfo.currencyCounterLowerCase + checkerInfo.currencyBaseLowerCase
        val tickerJsonObject = jsonObject.getJSONObject("data")
        val buyObject = tickerJsonObject.getJSONObject("compra")
        val sellObject = tickerJsonObject.getJSONObject("venta")
        ticker.ask = buyObject.getDouble(currencyCode)
        ticker.bid = sellObject.getDouble(currencyCode)
        ticker.last = ticker.ask
    }
}