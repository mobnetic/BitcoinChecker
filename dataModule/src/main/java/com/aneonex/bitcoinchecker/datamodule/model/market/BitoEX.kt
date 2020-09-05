package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONArray

class BitoEX : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BitoEX"
        private const val TTS_NAME = NAME
        private const val URL = "https://www.bitoex.com/sync/dashboard/%1\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.TWD
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, System.currentTimeMillis())
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val jsonArray = JSONArray(responseString)
        ticker.ask = jsonArray.getString(0).replace(",".toRegex(), "").toDouble()
        ticker.bid = jsonArray.getString(1).replace(",".toRegex(), "").toDouble()
        ticker.last = ticker.ask
        ticker.timestamp = java.lang.Long.valueOf(jsonArray.getString(2))
    }
}