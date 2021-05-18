package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject
import java.util.*

class EXX : SimpleMarket(
        "EXX",
        "https://api.exx.com/data/v1/markets",
        "https://api.exx.com/data/v1/ticker?currency=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        for (marketKey in jsonObject.keys()) {
            val assets = marketKey.uppercase(Locale.ROOT).split('_')
            if (assets.size != 2) continue

            pairs.add(
                CurrencyPairInfo(
                    assets[0], // Base currency
                    assets[1], // Quote currency
                    marketKey
                )
            )
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.timestamp = jsonObject.getLong("date")

        jsonObject.getJSONObject("ticker").let {
            ticker.last = it.getDouble("last")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.vol = it.getDouble("vol")

            ticker.bid = it.getDouble("buy")
            ticker.ask = it.getDouble("sell")
        }
    }
}