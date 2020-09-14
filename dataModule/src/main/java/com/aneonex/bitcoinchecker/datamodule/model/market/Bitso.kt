package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONObject
import java.util.*

class Bitso : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Bitso"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bitso.com/public/info"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.MXN
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.MXN
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.MXN
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.MXN
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        var pairId = checkerInfo.currencyPairId
        if (pairId == null) {
            pairId = checkerInfo.currencyBaseLowerCase + "_" + checkerInfo.currencyCounterLowerCase
        }
        val pairJsonObject = jsonObject.getJSONObject(pairId)
        ticker.bid = ParseUtils.getDoubleFromString(pairJsonObject, "buy")
        ticker.ask = ParseUtils.getDoubleFromString(pairJsonObject, "sell")
        ticker.vol = ParseUtils.getDoubleFromString(pairJsonObject, "volume")
        ticker.last = ParseUtils.getDoubleFromString(pairJsonObject, "rate")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairIds = jsonObject.names()!!
        for (i in 0 until pairIds.length()) {
            val pairId = pairIds.getString(i) ?: continue
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(CurrencyPairInfo(
                    currencies[0].toUpperCase(Locale.US),
                    currencies[1].toUpperCase(Locale.US),
                    pairId))
        }
    }
}