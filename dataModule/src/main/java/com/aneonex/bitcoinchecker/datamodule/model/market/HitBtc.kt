package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONObject

class HitBtc : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid")
        ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask")
        ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume")
        ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high")
        ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low")
        ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val symbolsJsonArray = jsonObject.getJSONArray("symbols")
        for (i in 0 until symbolsJsonArray.length()) {
            val pairJsonObject = symbolsJsonArray.getJSONObject(i)
            val currencyBase = pairJsonObject.getString("commodity")
            val currencyCounter = pairJsonObject.getString("currency")
            val currencyPairId = pairJsonObject.getString("symbol")
            if (currencyBase == null || currencyCounter == null || currencyPairId == null) continue
            pairs.add(CurrencyPairInfo(currencyBase, currencyCounter, currencyPairId))
        }
    }

    companion object {
        private const val NAME = "HitBTC"
        private const val TTS_NAME = "Hit BTC"
        private const val URL = "https://api.hitbtc.com/api/1/public/%1\$s/ticker"
        private const val URL_CURRENCY_PAIRS = "https://api.hitbtc.com/api/1/public/symbols"
    }
}