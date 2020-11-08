package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class Pdax : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "PDAX"
        private const val TTS_NAME = NAME
        private const val URL = "https://trade.pdax.ph/moon/v1/market/tick/ANX/%1\$s%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(Currency.PHP)
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(Currency.PHP)
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(Currency.PHP, VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(Currency.PHP, VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(Currency.PHP)
            CURRENCY_PAIRS[VirtualCurrency.USDT] = arrayOf(Currency.PHP)
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairId = "${checkerInfo.currencyBase}${checkerInfo.currencyCounter}"

        jsonObject
                .getJSONArray("data")
                .getJSONObject(0)
                .getJSONObject(pairId)
                .let {
                    ticker.last = it.getDouble("last")
                    ticker.vol = it.getDouble("volume")

                    ticker.high = it.getDouble("high")
                    ticker.low = it.getDouble("low")

                    // bid and ask is not real time in the response
                    // ticker.bid = it.getDouble("bid")
                    // ticker.ask = it.getDouble("ask")

                    ticker.timestamp = it.getLong("timestampMillis")
                }
    }
}