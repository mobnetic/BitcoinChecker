package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class CoinEgg : SimpleMarket(
    "CoinEgg",
    "https://api.coinegg.fun/openapi/brokerInfo?type=token",
    "https://api.coinegg.fun/openapi/quote/v1/ticker/24hr?symbol=%1\$s",
    "Coin egg"
) {

    override fun parseCurrencyPairsFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        jsonObject.getJSONArray("symbols")
            .forEachJSONObject { market ->
                if (market.getString("status") == "TRADING") {
                    pairs.add(
                        CurrencyPairInfo(
                            market.getString("baseAssetName"),
                            market.getString("quoteAssetName"),
                            market.getString("symbol")
                        )
                    )
                }
            }
    }

    override fun parseTickerFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        ticker.apply {
            last = jsonObject.getDouble("lastPrice")

            high = jsonObject.getDouble("lastPrice")
            low = jsonObject.getDouble("lastPrice")

            vol = jsonObject.getDouble("volume")
            volQuote = jsonObject.getDouble("quoteVolume")

            bid = jsonObject.getDouble("bestBidPrice")
            ask = jsonObject.getDouble("bestAskPrice")

            timestamp = jsonObject.getLong("time")
        }
    }
}