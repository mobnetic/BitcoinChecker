package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject
import java.util.*

class Huobi : SimpleMarket(
    "Huobi",
    "https://api.huobi.pro/v1/common/symbols",
    "https://api.huobi.pro/market/detail/merged?symbol=%1\$s"
) {

    override fun getPairId(checkerInfo: CheckerInfo): String {
        return checkerInfo.currencyPairId ?:  checkerInfo.currencyBaseLowerCase + checkerInfo.currencyCounterLowerCase
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("tick").also {
            ticker.bid = it.getJSONArray("bid").getDouble(0)
            ticker.ask = it.getJSONArray("ask").getDouble(0)
            ticker.vol = it.getDouble("amount")
            ticker.volQuote = it.getDouble("vol")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("close")
        }
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        if ("ok".equals(jsonObject.getString("status"), ignoreCase = true)) {
            jsonObject.getJSONArray("data")
                .forEachJSONObject { market ->
                    pairs.add(CurrencyPairInfo(
                        market.getString("base-currency").uppercase(Locale.ROOT),
                        market.getString("quote-currency").uppercase(Locale.ROOT),
                        market.getString("symbol")))
                }
        } else {
            throw Exception("Parse currency pairs error.")
        }
    }
}