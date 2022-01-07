package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class BigONE : SimpleMarket(
        "BigONE",
        "https://big.one/api/v3/asset_pairs",
        "https://big.one/api/v3/asset_pairs/%1\$s/ticker",
        "Big One"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("data").forEachJSONObject { market ->
            pairs.add(CurrencyPairInfo(
                market.getJSONObject("base_asset").getString("symbol"),
                market.getJSONObject("quote_asset").getString("symbol"),
                market.getString("name")
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONObject("data")
            .let {
                ticker.last = it.getDouble("close")
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")
                ticker.vol = it.getDouble("volume")

                ticker.bid = it.getJSONObject("bid").getDouble("price")
                ticker.ask = it.getJSONObject("ask").getDouble("price")
            }
    }
}