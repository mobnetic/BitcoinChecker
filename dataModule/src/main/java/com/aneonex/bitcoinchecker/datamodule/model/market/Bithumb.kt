package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class Bithumb : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Bithumb"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://api.bithumb.com/public/ticker/%1\$s"
        private const val URL_ORDERS = "https://api.bithumb.com/public/orderbook/%1\$s?count=1"
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
            CURRENCY_PAIRS[VirtualCurrency.DASH] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.XMR] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.ZEC] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.QTUM] = arrayOf(
                    Currency.KRW
            )
            CURRENCY_PAIRS[VirtualCurrency.CON] = arrayOf(
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
        val dataObject = jsonObject.getJSONObject("data")
        if (requestId == 0) {
            ticker.vol = dataObject.getDouble("units_traded_24H")
            ticker.high = dataObject.getDouble("max_price")
            ticker.low = dataObject.getDouble("min_price")
            ticker.last = dataObject.getDouble("closing_price")
            ticker.timestamp = dataObject.getLong("date")
        } else {
            ticker.bid = getFirstPriceFromOrder(dataObject, "bids")
            ticker.ask = getFirstPriceFromOrder(dataObject, "asks")
        }
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject,
                                          checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("message")
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