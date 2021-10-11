package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachName
import org.json.JSONObject

class CoinJar : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONObject("exchange_rates")
            .getJSONObject(checkerInfo.currencyPairId!!).also {
                ticker.bid = it.optDouble("bid", ticker.bid)
                ticker.ask = it.optDouble("ask", ticker.ask)
                ticker.last = it.getDouble("midpoint")
            }
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject
            .getJSONObject("exchange_rates")
            .forEachName{ symbol, item ->
                pairs.add(CurrencyPairInfo(
                    item.getString("base_currency"),
                    item.getString("counter_currency"),
                    symbol
                ))
            }
    }

    companion object {
        private const val NAME = "CoinJar Prices"
        private const val TTS_NAME = "Coin Jar prices"
        private const val URL = "https://api.coinjar.com/v3/exchange_rates"
        private const val URL_CURRENCY_PAIRS = URL
    }
}