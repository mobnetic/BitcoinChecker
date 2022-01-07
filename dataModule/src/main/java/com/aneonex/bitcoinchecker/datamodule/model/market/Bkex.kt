package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject

class Bkex : SimpleMarket(
    "BKEX",
    "https://api.bkex.com/v2/common/symbols",
    "https://api.bkex.com/v2/q/tickers?symbol=%1\$s"
) {
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            if(!market.getBoolean("supportTrade"))
                continue

            val pairId = market.getString("symbol")

            // Split pairs like BTC_USDT
            val assets = pairId.split('_')

            if(assets.size != 2)
                continue

            pairs.add( CurrencyPairInfo(
                    assets[0],
                    assets[1],
                    pairId,
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONArray("data")
            .getJSONObject(0)
            .also {
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")
                ticker.last = it.getDouble("close")
                ticker.vol = it.getDouble("volume")
                ticker.timestamp = it.getLong("ts")
            }
    }
}