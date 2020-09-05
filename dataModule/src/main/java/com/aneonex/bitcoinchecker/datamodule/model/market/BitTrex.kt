package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class BitTrex : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyCounter, checkerInfo.currencyBase) // reversed
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val resultJsonObject = jsonObject.getJSONObject("result")
        ticker.bid = resultJsonObject.getDouble("Bid")
        ticker.ask = resultJsonObject.getDouble("Ask")
        ticker.last = resultJsonObject.getDouble("Last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val resultJsonArray = jsonObject.getJSONArray("result")
        for (i in 0 until resultJsonArray.length()) {
            val marketJsonObject = resultJsonArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    marketJsonObject.getString("MarketCurrency"),  // reversed
                    marketJsonObject.getString("BaseCurrency"),  // reversed
                    marketJsonObject.getString("MarketName")))
        }
    }

    companion object {
        private const val NAME = "BitTrex"
        private const val TTS_NAME = "Bit Trex"
        private const val URL = "https://bittrex.com/api/v1.1/public/getticker?market=%1\$s-%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://bittrex.com/api/v1.1/public/getmarkets"
    }
}