package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class Bitkub : SimpleMarket(
        "Bitkub",
        "https://api.bitkub.com/api/market/symbols",
        "https://api.bitkub.com/api/market/ticker?sym=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("result").forEachJSONObject { market ->
            val symbol = market.getString("symbol")
            val assets = symbol.split('_')
            if(assets.size == 2) {
                pairs.add(
                    CurrencyPairInfo(
                        assets[1],
                        assets[0],
                        symbol
                    )
                )
            }
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONObject(checkerInfo.currencyPairId!!)
            .let {
                ticker.last = it.getDouble("last")

                ticker.high = it.getDouble("high24hr")
                ticker.low = it.getDouble("low24hr")

                ticker.vol = it.getDouble("baseVolume")
                ticker.volQuote = it.getDouble("quoteVolume")

                ticker.bid = it.getDouble("highestBid")
                ticker.ask = it.getDouble("lowestAsk")
            }
    }
}