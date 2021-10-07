package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.FuturesContractType
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class FtxFutures : SimpleMarket(
    "FTX Futures",
    "https://ftx.com/api/futures",
    "https://ftx.com/api/futures/%1\$s"
) {
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("result").forEachJSONObject {  market ->
            if(market.getString("type") != "perpetual")
                return@forEachJSONObject

            pairs.add( CurrencyPairInfo(
                market.getString("underlying"),
                "USD",
                market.getString("name"),
                FuturesContractType.PERPETUAL
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("result").also { market ->
            ticker.bid = market.getDouble("bid")
            ticker.ask = market.getDouble("ask")
            ticker.last = market.getDouble("last")

            if(ticker.last > 0) {
                ticker.volQuote = market.getDouble("volumeUsd24h")
                ticker.vol = ticker.volQuote / ticker.last // Calculated base volume
            }
        }
    }
}