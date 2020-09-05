package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class GateCoin : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairNames = jsonObject.getJSONArray("tickers") //returned JSON text is an ARRAY of JSONObjects
        val userCurrencyPairChoice = checkerInfo.currencyBase + checkerInfo.currencyCounter
        //each JSONObject in the Array has a currency pair and its corresponding ticker details
        for (i in 0 until pairNames.length()) {
            val tickerDetails = pairNames.getJSONObject(i)
            val currentPairId = tickerDetails.getString("currencyPair")
            if (currentPairId == userCurrencyPairChoice) {
                ticker.bid = tickerDetails.getDouble("bid")
                ticker.ask = tickerDetails.getDouble("ask")
                ticker.vol = tickerDetails.getDouble("volume")
                ticker.high = tickerDetails.getDouble("high")
                ticker.low = tickerDetails.getDouble("low")
                ticker.last = tickerDetails.getDouble("last")
                ticker.timestamp = tickerDetails.getLong("createDateTime")
                break
            }
        }
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairNames = jsonObject.getJSONArray("tickers") //returned JSON text is an ARRAY of JSONObjects
        for (i in 0 until pairNames.length()) {
            //each JSONObject in the Array has a currency pair and its corresponding ticker details
            val pairId = pairNames.getJSONObject(i).getString("currencyPair") ?: continue
            //split by index - use char positions (start, end+1) as index
            val baseCurrency = pairId.substring(0, 3) //base currency
            val counterCurrency = pairId.substring(3, 6) //counter currency

            if(baseCurrency.isEmpty() || counterCurrency.isEmpty()) continue

            pairs.add(CurrencyPairInfo(baseCurrency, counterCurrency, pairId))
        }
    }

    companion object {
        private const val NAME = "GateCoin"
        private const val TTS_NAME = "Gate Coin"
        private const val URL = "https://api.gatecoin.com/Public/LiveTickers"
    }
}