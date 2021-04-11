package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray
import org.json.JSONObject

class Foxbit : SimpleMarket(
        "Foxbit",
        "https://watcher.foxbit.com.br/api/Ticker",
        "https://watcher.foxbit.com.br/api/Ticker?exchange=Foxbit&Pair=%1\$s"
        ) {

    override fun parseCurrencyPairs(
        requestId: Int,
        responseString: String,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        val markets = JSONArray(responseString)

        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            if(market.getString("exchange") != "Foxbit")
                continue

            val pairId = market.getString("currency")
            val separatorIndex = pairId.indexOf('X')
            if(separatorIndex <= 0)
                continue

            val asset1 = pairId.substring(0, separatorIndex)
            val asset2 = pairId.substring(separatorIndex+1, pairId.length)

            val firstAssetIsQuoteCurrency: Boolean =
                when{
                    asset1 == "BRL" -> true
                    asset2 == "BRL" -> false
                    asset2 == "USDT" -> false
                    asset2 == "BTC" -> false
                    else -> continue // Unknown pair format
                }

            val baseCurrency = if(firstAssetIsQuoteCurrency) asset2 else asset1
            val quoteCurrency = if(firstAssetIsQuoteCurrency) asset1 else asset2

            pairs.add(CurrencyPairInfo(
                baseCurrency,
                quoteCurrency,
                pairId
            ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .let {
                ticker.vol = it.getDouble("vol")
                if(ticker.vol <= 0)
                    throw throw MarketParseException("No trading volume")

                ticker.last = it.getDouble("last")
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")

                ticker.bid = it.getDouble("buyPrice")
                ticker.ask = it.getDouble("sellPrice")

                ticker.timestamp = TimeUtils.convertISODateToTimestamp(it.getString("createdDate") + "Z")
            }
    }
}