package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject

class OceanEx : SimpleMarket(
        "OceanEx",
        "https://api.oceanex.pro/v1/markets",
        "https://api.oceanex.pro/v1/tickers/%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val data = jsonObject.getJSONArray("data")

        for (i in 0 until data.length()) {
            val market = data.getJSONObject(i)

            val assets = market.getString("name").split('/')
            if(assets.size != 2) continue

            pairs.add(CurrencyPairInfo(
                assets[0], // Base currency
                assets[1], // Quote currency
                market.getString("id")
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val data = jsonObject.getJSONObject("data")
        ticker.timestamp = data.getLong("at")

        data.getJSONObject("ticker").let {
            ticker.last = it.getDouble("last")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.vol = it.getDouble("vol")

            ticker.bid = it.getDouble("buy")
            ticker.ask = it.getDouble("sell")
        }
    }
}