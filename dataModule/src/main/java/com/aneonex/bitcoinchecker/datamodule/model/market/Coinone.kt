package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Coinone : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Coinone"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://api.coinone.co.kr/ticker?currency=%1\$s"
        private const val URL_ORDERS = "https://api.coinone.co.kr/orderbook?currency=%1\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.ETC] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.QTUM] = arrayOf(
                    Currency.KRW
            )
        }
    }

    override fun getNumOfRequests(checkerRecord: CheckerInfo?): Int {
        return 2
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return if (requestId == 0) {
            String.format(URL_TICKER, checkerInfo.currencyBaseLowerCase)
        } else {
            String.format(URL_ORDERS, checkerInfo.currencyBaseLowerCase)
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker,
                                           checkerInfo: CheckerInfo) {
        if (requestId == 0) {
            ticker.vol = jsonObject.getDouble("volume")
            ticker.high = jsonObject.getDouble("high")
            ticker.low = jsonObject.getDouble("low")
            ticker.last = jsonObject.getDouble("last")
            ticker.timestamp = jsonObject.getLong("timestamp")
        } else {
            ticker.bid = getFirstPriceFromOrder(jsonObject, "bid")
            ticker.ask = getFirstPriceFromOrder(jsonObject, "ask")
        }
    }

    @Throws(Exception::class)
    private fun getFirstPriceFromOrder(jsonObject: JSONObject, key: String): Double {
        val array = jsonObject.getJSONArray(key)
        if (array.length() == 0) {
            return Ticker.NO_DATA.toDouble()
        }
        val first = array.getJSONObject(0)
        return first.getDouble("price")
    }
}