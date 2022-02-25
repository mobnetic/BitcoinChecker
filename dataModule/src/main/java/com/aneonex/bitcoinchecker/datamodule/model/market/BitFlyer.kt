package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class BitFlyer : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "bitFlyer"
        private const val TTS_NAME = "bit flyer"
        private const val URL = "https://api.bitflyer.jp/v1/ticker?product_code=%1\$s_%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(Currency.JPY)
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(Currency.JPY, VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(Currency.JPY)
            CURRENCY_PAIRS["MONA"] = arrayOf(Currency.JPY)
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(VirtualCurrency.BTC)
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("best_bid")
        ticker.ask = jsonObject.getDouble("best_ask")
        ticker.vol = jsonObject.getDouble("volume_by_product")
        ticker.last = jsonObject.getDouble("ltp")
    }
}