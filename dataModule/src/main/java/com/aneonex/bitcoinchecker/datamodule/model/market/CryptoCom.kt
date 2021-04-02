package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class CryptoCom : SimpleMarket(
        "Crypto.com",
        "https://api.crypto.com/v2/public/get-instruments",
        "https://api.crypto.com/v2/public/get-ticker?instrument_name=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject
            .getJSONObject("result")
            .getJSONArray("instruments")

        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            pairs.add(CurrencyPairInfo(
                market.getString("base_currency"),
                market.getString("quote_currency"),
                market.getString("instrument_name")
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONObject("result")
            .getJSONObject("data")
            .let {
                ticker.vol = it.getDouble("v")
                if(ticker.vol <= 0)
                    throw MarketParseException("No trading volume")

                ticker.last = it.getDouble("a")
                ticker.high = it.getDouble("h")
                ticker.low = it.getDouble("l")

                ticker.bid = it.getDouble("b")
                ticker.ask = it.getDouble("k")

                ticker.timestamp = it.getLong("t")
            }
    }
}