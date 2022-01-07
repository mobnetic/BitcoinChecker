package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

class ProBit : SimpleMarket(
        "ProBit",
        "https://api.probit.com/api/exchange/v1/market",
        "https://api.probit.com/api/exchange/v1/ticker?market_ids=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")

        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            if(market.getBoolean("closed"))
                continue

            pairs.add(CurrencyPairInfo(
                    market.getString("base_currency_id"),
                    market.getString("quote_currency_id"),
                    market.getString("id")
                ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONArray("data")
            .getJSONObject(0)
            .let {
                ticker.last = it.getDouble("last")
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")
                ticker.vol = it.getDouble("base_volume")

                ticker.timestamp = TimeUtils.convertISODateToTimestamp(it.getString("time"))
            }
    }
}