package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class Bittrex : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bittrex"
        private const val TTS_NAME = NAME
        private const val URL = "https://bittrex.com/api/v1.1/public/getticker?market=%1\$s-%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://bittrex.com/api/v1.1/public/getmarkets"
    }

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
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("result").forEachJSONObject { marketJsonObject ->
            pairs.add(CurrencyPairInfo(
                    marketJsonObject.getString("MarketCurrency"),  // reversed
                    marketJsonObject.getString("BaseCurrency"),  // reversed
                    marketJsonObject.getString("MarketName")))
        }
    }
}