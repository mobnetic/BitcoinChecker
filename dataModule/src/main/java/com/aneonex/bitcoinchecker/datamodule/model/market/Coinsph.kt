package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Coinsph : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val marketJsonObject = jsonObject.getJSONObject("market")
        ticker.bid = marketJsonObject.getDouble("bid")
        ticker.ask = marketJsonObject.getDouble("ask")
        ticker.last = ticker.ask
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJsonArray = jsonObject.getJSONArray("markets")
        for (i in 0 until marketsJsonArray.length()) {
            val pairJsonObject = marketsJsonArray.getJSONObject(i)
            if (pairJsonObject != null) {
                pairs.add(CurrencyPairInfo(
                        pairJsonObject.getString("product"),
                        pairJsonObject.getString("currency"),
                        pairJsonObject.getString("symbol")
                ))
            }
        }
    }

    companion object {
        private const val NAME = "Coins.ph"
        private const val TTS_NAME = NAME
        private const val URL = "https://quote.coins.ph/v1/markets/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://quote.coins.ph/v1/markets"
    }
}