package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.R
import org.json.JSONObject

class CryptoRush : Market(NAME, TTS_NAME, null) {
    override val cautionResId: Int
        get() = R.string.market_caution_much_data

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairJsonObject = jsonObject.getJSONObject(checkerInfo.currencyBase + "/" + checkerInfo.currencyCounter)
        ticker.bid = pairJsonObject.getDouble("current_bid")
        ticker.ask = pairJsonObject.getDouble("current_ask")
        ticker.vol = pairJsonObject.getDouble("volume_base_24h")
        ticker.high = pairJsonObject.getDouble("highest_24h")
        ticker.low = pairJsonObject.getDouble("lowest_24h")
        ticker.last = pairJsonObject.getDouble("last_trade")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairNames = jsonObject.names()!!
        for (i in 0 until pairNames.length()) {
            val pairId = pairNames.getString(i) ?: continue
            val currencies = pairId.split("/".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(CurrencyPairInfo(currencies[0], currencies[1], pairId))
        }
    }

    companion object {
        const val NAME = "CryptoRush"
        const val TTS_NAME = "Crypto Rush"
        const val URL = "https://cryptorush.in/marketdata/all.json"
        private const val URL_CURRENCY_PAIRS = URL
    }
}