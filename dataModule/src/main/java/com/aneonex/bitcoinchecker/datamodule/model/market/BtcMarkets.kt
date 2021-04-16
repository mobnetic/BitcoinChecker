package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray
import org.json.JSONObject

class BtcMarkets : SimpleMarket(
        "BtcMarkets.net",
        "https://api.btcmarkets.net/v3/markets",
        "https://api.btcmarkets.net/v3/markets/%1\$s/ticker",
        "BTC Markets net"
) {
    override fun getPairId(checkerInfo: CheckerInfo): String {
        // Compatibility with old app version
        return checkerInfo.currencyPairId ?: "${checkerInfo.currencyBase}-${checkerInfo.currencyCounter}"
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)

        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            pairs.add(CurrencyPairInfo(
                    market.getString("baseAssetName"),
                    market.getString("quoteAssetName"),
                    market.getString("marketId")
            ))
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.apply {
            last = jsonObject.getDouble("lastPrice")
            bid = jsonObject.getDouble("bestBid")
            ask = jsonObject.getDouble("bestAsk")

            low = jsonObject.getDouble("low24h")
            high = jsonObject.getDouble("high24h")

            vol = jsonObject.getDouble("volume24h")
            // volQuote = jsonObject.getDouble("volumeQte24h")

            timestamp = TimeUtils.convertISODateToTimestamp(jsonObject.getString("timestamp"))
        }
    }
}
