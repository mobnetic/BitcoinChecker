package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class Latoken : SimpleMarket(
        "LATOKEN",
        "https://api.latoken.com/v2/ticker",
        "https://api.latoken.com/v2/ticker/%1\$s",
        "Latoken"
        ) {

    override fun parseCurrencyPairs(
        requestId: Int,
        responseString: String,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        val pairsJson = JSONArray(responseString)
        pairsJson.forEachJSONObject {
            val symbol = it.getString("symbol")
            val assets = symbol.split('/')
            if(assets.size == 2) {
                val baseAsset = assets[0]
                val quoteAsset = assets[1]

                pairs.add(
                    CurrencyPairInfo(
                        baseAsset,
                        quoteAsset,
                        symbol
                    )
                )
            }
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .let {
                ticker.last = it.getDouble("lastPrice")

                ticker.vol = it.getDouble("amount24h")
                ticker.volQuote = it.getDouble("volume24h")

                ticker.bid = it.getDouble("bestBid")
                ticker.ask = it.getDouble("bestAsk")

                ticker.timestamp = it.getLong("updateTimestamp")
            }
    }
}