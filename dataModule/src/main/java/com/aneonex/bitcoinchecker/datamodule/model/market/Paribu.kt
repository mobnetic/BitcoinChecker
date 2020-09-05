package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONObject

class Paribu : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        const val NAME = "Paribu"
        const val TTS_NAME = NAME
        const val URL = "https://www.paribu.com/ticker"
        val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.TRY
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJsonObject = jsonObject.getJSONObject("BTC_TL")
        ticker.bid = ParseUtils.getDoubleFromString(dataJsonObject, "highestBid")
        ticker.ask = ParseUtils.getDoubleFromString(dataJsonObject, "lowestAsk")
        ticker.vol = ParseUtils.getDoubleFromString(dataJsonObject, "volume")
        ticker.high = ParseUtils.getDoubleFromString(dataJsonObject, "high24hr")
        ticker.low = ParseUtils.getDoubleFromString(dataJsonObject, "low24hr")
        ticker.last = ParseUtils.getDoubleFromString(dataJsonObject, "last")
    }
}