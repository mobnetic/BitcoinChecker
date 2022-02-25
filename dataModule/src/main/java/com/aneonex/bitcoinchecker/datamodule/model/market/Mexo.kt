package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject

class Mexo : SimpleMarket(
    "Mexo",
    "https://api.mexo.io/openapi/v1/brokerInfo",
    "https://api.mexo.io/openapi/quote/v1/ticker/24hr?symbol=%1\$s"
) {
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("symbols")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            pairs.add( CurrencyPairInfo(
                    market.getString("baseAsset"),
                    market.getString("quoteAsset"),
                    market.getString("symbol")
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        with(jsonObject) {
            ticker.ask = getDouble("bestAskPrice")
            ticker.bid = getDouble("bestBidPrice")

            ticker.high = getDouble("highPrice")
            ticker.low = getDouble("lowPrice")

            ticker.last = getDouble("lastPrice")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}