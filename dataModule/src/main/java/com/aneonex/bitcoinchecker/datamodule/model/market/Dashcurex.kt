package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Dashcurex : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Dashcurex"
        private const val TTS_NAME = NAME
        private const val URL = "https://dashcurex.com/api/%1\$s/ticker.json"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.DASH] = arrayOf(
                    Currency.PLN,
                    Currency.USD
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = parsePrice(jsonObject.getDouble("best_ask"))
        ticker.ask = parsePrice(jsonObject.getDouble("best_bid"))
        ticker.vol = parseBTC(jsonObject.getDouble("total_volume"))
        ticker.high = parsePrice(jsonObject.getDouble("highest_tx_price"))
        ticker.low = parsePrice(jsonObject.getDouble("lowest_tx_price"))
        ticker.last = parsePrice(jsonObject.getDouble("last_tx_price"))
    }

    private fun parsePrice(price: Double): Double {
        return price / 10000
    }

    private fun parseBTC(satoshi: Double): Double {
        return satoshi / 100000000
    }
}