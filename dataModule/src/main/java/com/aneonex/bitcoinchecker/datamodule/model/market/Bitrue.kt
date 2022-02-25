package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class Bitrue : SimpleMarket(
        "Bitrue",
        "https://openapi.bitrue.com/api/v1/exchangeInfo",
        "https://openapi.bitrue.com/api/v1/ticker/24hr?symbol=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject
            .getJSONArray("symbols")
            .forEachJSONObject { market ->
                if(market.getString("status") == "TRADING") {
                    pairs.add(
                        CurrencyPairInfo(
                            market.getString("baseAsset").uppercase(),
                            market.getString("quoteAsset").uppercase(),
                            market.getString("symbol")
                        )
                    )
                }
            }
    }

    override fun parseTicker(
        requestId: Int,
        responseString: String,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        val jsonObject = JSONArray(responseString).let {
            if(it.length() != 1)
                throw MarketParseException("No data")

            it.getJSONObject(0)
        }

        jsonObject
            .also {
                ticker.last = it.getDouble("lastPrice")

                ticker.high = it.getDouble("highPrice")
                ticker.low = it.getDouble("lowPrice")

                // ticker.vol = it.getDouble("volume") // Returns same as quoteVolume (exchange bug?)
                ticker.volQuote = it.getDouble("quoteVolume")

                ticker.bid = it.optDouble("bidPrice", ticker.bid)
                ticker.ask = it.optDouble("askPrice", ticker.ask)
            }
    }
}