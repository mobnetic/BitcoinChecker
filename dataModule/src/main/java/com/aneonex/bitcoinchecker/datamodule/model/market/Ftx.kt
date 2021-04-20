package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Ftx : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "FTX"
        private const val TTS_NAME = "FTX"
        private const val URL = "https://ftx.com/api/markets/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://ftx.com/api/markets"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("result")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            if(market.getString("type") != "spot") continue

            pairs.add( CurrencyPairInfo(
                    market.getString("baseCurrency"),
                    market.getString("quoteCurrency"),
                    market.getString("name"),
            ))
        }

    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val market = jsonObject.getJSONObject("result")

        ticker.bid = market.getDouble("bid")
        ticker.ask = market.getDouble("ask")
        ticker.last = market.getDouble("last")

        if(ticker.last > 0) {
            ticker.volQuote = market.getDouble("quoteVolume24h")
            ticker.vol = ticker.volQuote / ticker.last // Calculated base volume

        }
    }
}