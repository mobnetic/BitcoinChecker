package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

/**
 * Implements SurBTC connection to the API
 * @author Eduardo Laguna
 */
class SurBtc : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = tickerJsonObject.getJSONArray("max_bid").getDouble(0)
        ticker.ask = tickerJsonObject.getJSONArray("min_ask").getDouble(0)
        ticker.vol = tickerJsonObject.getJSONArray("volume").getDouble(0)
        ticker.last = tickerJsonObject.getJSONArray("last_price").getDouble(0)
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJsonArray = jsonObject.getJSONArray("markets")
        for (i in 0 until marketsJsonArray.length()) {
            val marketJsonObject = marketsJsonArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    marketJsonObject.getString("base_currency"),
                    marketJsonObject.getString("quote_currency"),
                    marketJsonObject.getString("id")))
        }
    }

    companion object {
        private const val NAME = "SurBtc"
        private const val TTS_NAME = "Sur BTC"
        private const val URL = "https://www.surbtc.com/api/v2/markets/%1\$s/ticker.json"
        private const val URL_CURRENCY_PAIRS = "https://www.surbtc.com/api/v2/markets.json"
    }
}