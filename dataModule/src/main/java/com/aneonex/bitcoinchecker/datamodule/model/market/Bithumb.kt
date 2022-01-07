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
            val marketAll = arrayOf(Currency.KRW, VirtualCurrency.BTC)
            val marketKrw = arrayOf(Currency.KRW)
            val marketBtc = arrayOf(VirtualCurrency.BTC)

            CURRENCY_PAIRS[VirtualCurrency.BTC] = marketKrw
            CURRENCY_PAIRS[VirtualCurrency.ETH] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.ETC] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.LTC] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.XRP] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.BCH] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.DOGE] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.SOL] = marketAll

            CURRENCY_PAIRS[VirtualCurrency.QTUM] = marketKrw
            CURRENCY_PAIRS[VirtualCurrency.CON] = marketKrw
            CURRENCY_PAIRS[VirtualCurrency.TRX] = marketKrw

            CURRENCY_PAIRS["LN"] = marketBtc
            CURRENCY_PAIRS["IBP"] = marketBtc
            CURRENCY_PAIRS["BFC"] = marketAll

            CURRENCY_PAIRS[VirtualCurrency.EOS] = marketAll
            CURRENCY_PAIRS[VirtualCurrency.LINK] = marketKrw

            CURRENCY_PAIRS["WEMIX"] = marketKrw
            CURRENCY_PAIRS["BORA"] = marketKrw
            CURRENCY_PAIRS["ASM"] = marketKrw
            CURRENCY_PAIRS["SAND"] = marketKrw
            CURRENCY_PAIRS["MANA"] = marketKrw
            CURRENCY_PAIRS["YFI"] = marketKrw
            CURRENCY_PAIRS["DVI"] = marketKrw
            CURRENCY_PAIRS["ETC"] = marketKrw
            CURRENCY_PAIRS["DOT"] = marketKrw
            CURRENCY_PAIRS["LUNA"] = marketKrw
        }
    }

    override fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 2
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val pairId = "${checkerInfo.currencyBaseLowerCase}_${checkerInfo.currencyCounterLowerCase}"

        return if (requestId == 0) {
            String.format(URL_TICKER, pairId)
        } else {
            String.format(URL_ORDERS, pairId)
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