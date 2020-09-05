package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONObject

class Zaydo : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Zaydo"
        private const val TTS_NAME = NAME
        private const val URL = "http://chart.zyado.com/ticker.json"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.EUR
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid")
        ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask")
        ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume")
        ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high")
        ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low")
        ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last")
    }
}