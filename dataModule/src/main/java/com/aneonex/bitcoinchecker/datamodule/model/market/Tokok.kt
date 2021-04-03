package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject
import java.util.*

class Tokok : SimpleMarket(
        "TOKOK",
        "https://www.tokok.com/api/v1/tickers",
        "https://www.tokok.com/api/v1/ticker?symbol=%1\$s",
        "Tokok"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("ticker")

        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            val symbol = market.getString("symbol")
            val assets = symbol.split('_')
            if(assets.size != 2) continue

            pairs.add(CurrencyPairInfo(
                assets[0], // Base currency
                assets[1], // Quote currency
                symbol.toLowerCase(Locale.ROOT)
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.timestamp = jsonObject.getLong("timestamp")

        jsonObject
            .getJSONObject("ticker")
            .let {
                ticker.last = it.getDouble("last")
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")
                ticker.vol = it.getDouble("vol")

                ticker.bid = it.getDouble("buy")
                ticker.ask = it.getDouble("sell")
            }
    }
}