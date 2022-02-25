package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class Mexc : SimpleMarket(
    "MEXC Global",
    "https://www.mexc.com/open/api/v2/market/symbols",
    "https://www.mexc.com/open/api/v2/market/ticker?symbol=%1\$s",
    "Mexc"
) {

    override fun parseCurrencyPairsFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        jsonObject
            .getJSONArray("data")
            .forEachJSONObject { market ->
                if (market.getString("state") == "ENABLED") {
                    val symbol = market.getString("symbol")
                    val assets = symbol.split('_')
                    if (assets.size == 2) {
                        pairs.add(
                            CurrencyPairInfo(
                                assets[0], // Base
                                assets[1], // Quote
                                symbol
                            )
                        )
                    }
                }
            }
    }

    override fun parseTickerFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        jsonObject
            .getJSONArray("data")
            .getJSONObject(0)
            .also {
                ticker.last = it.getDouble("last")
                ticker.vol = it.getDouble("volume")

                ticker.timestamp = it.getLong("time")

                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")

                ticker.bid = it.getDouble("bid")
                ticker.ask = it.getDouble("ask")
            }
    }
}