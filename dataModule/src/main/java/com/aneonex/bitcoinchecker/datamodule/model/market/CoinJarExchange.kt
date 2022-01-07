package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class CoinJarExchange : SimpleMarket(
    "CoinJar Exchange",
    "https://api.exchange.coinjar.com/products",
    "https://data.exchange.coinjar.com/products/%1\$s/ticker",
    "Coin Jar exchange") {

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.apply {
            bid = jsonObject.getDouble("bid")
            ask = jsonObject.getDouble("ask")
            last = jsonObject.getDouble("last")
            vol = jsonObject.getDouble("volume_24h")
            timestamp = TimeUtils.convertISODateToTimestamp(jsonObject.getString("current_time"))
        }
    }

    override fun parseCurrencyPairs(
        requestId: Int,
        responseString: String,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        JSONArray(responseString).forEachJSONObject {
            val assets = it.getString("name").split('/')
            if(assets.size == 2) {
                pairs.add(
                    CurrencyPairInfo(
                        assets[0],
                        assets[1],
                        it.getString("id")
                    )
                )
            }
        }
    }
}