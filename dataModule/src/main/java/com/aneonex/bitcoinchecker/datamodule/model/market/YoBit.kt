package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.R
import com.aneonex.bitcoinchecker.datamodule.util.forEachString
import org.json.JSONObject
import java.util.*

class YoBit : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "YoBit"
        private const val TTS_NAME = NAME
        private const val URL = "https://yobit.net/api/3/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://yobit.net/api/3/info"
    }

    override val cautionResId: Int
        get() = R.string.market_caution_yobit

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val names = jsonObject.names()!!
        val tickerJsonObject = jsonObject.getJSONObject(names.getString(0))
        ticker.bid = tickerJsonObject.getDouble("sell")
        ticker.ask = tickerJsonObject.getDouble("buy")
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
        ticker.timestamp = tickerJsonObject.getLong("updated")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONObject("pairs").names()!!.forEachString { pairId ->
            val currencies = pairId.split('_')
            if (currencies.size == 2) {
                val currencyBase = currencies[0].uppercase(Locale.ROOT)
                val currencyCounter = currencies[1].uppercase(Locale.ROOT)
                pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, pairId))
            }
        }
    }
}