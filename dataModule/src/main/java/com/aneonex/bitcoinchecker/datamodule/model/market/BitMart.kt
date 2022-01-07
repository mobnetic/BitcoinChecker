package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject

class BitMart : SimpleMarket(
        "BitMart",
        "https://api-cloud.bitmart.com/spot/v1/symbols",
        "https://api-cloud.bitmart.com/spot/v1/ticker?symbol=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONObject("data").getJSONArray("symbols")

        for (i in 0 until markets.length()) {
            val market = markets.getString(i)

            val assets = market.split('_')
            if(assets.size != 2) continue

            pairs.add(CurrencyPairInfo(
                assets[0], // Base currency
                assets[1], // Quote currency
                market
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONObject("data")
            .getJSONArray("tickers")
            .getJSONObject(0)
            .let {
                ticker.last = it.getDouble("last_price")
                ticker.high = it.getDouble("high_24h")
                ticker.low = it.getDouble("low_24h")
                ticker.vol = it.getDouble("base_volume_24h")

                ticker.bid = it.getDouble("best_bid")
                ticker.ask = it.getDouble("best_ask")
            }
    }
}