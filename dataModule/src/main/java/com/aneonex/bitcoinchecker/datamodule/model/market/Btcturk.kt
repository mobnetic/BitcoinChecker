package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray

class Btcturk : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BtcTurk"
        private const val TTS_NAME = "Btc Turk"
        private const val URL = "https://www.btcturk.com/api/ticker"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.TRY
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.TRY
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonArray = JSONArray(responseString)
        val pairId = checkerInfo.currencyBase + checkerInfo.currencyCounter
        for (i in 0 until tickerJsonArray.length()) {
            val tickerJsonObject = tickerJsonArray.getJSONObject(i)
            if (pairId == tickerJsonObject.getString("pair")) {
                ticker.bid = tickerJsonObject.getDouble("bid")
                ticker.ask = tickerJsonObject.getDouble("ask")
                ticker.vol = tickerJsonObject.getDouble("volume")
                ticker.high = tickerJsonObject.getDouble("high")
                ticker.low = tickerJsonObject.getDouble("low")
                ticker.last = tickerJsonObject.getDouble("last")
                ticker.timestamp = (tickerJsonObject.getDouble("timestamp") * TimeUtils.MILLIS_IN_SECOND).toLong()
                break
            }
        }
    }
}