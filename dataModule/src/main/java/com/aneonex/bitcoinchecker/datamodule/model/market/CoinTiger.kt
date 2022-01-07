package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject
import java.util.*

class CoinTiger : SimpleMarket(
        "CoinTiger",
        "https://api.cointiger.com/exchange/trading/api/v2/currencys/v2",
        "https://api.cointiger.com/exchange/trading/api/market/detail?symbol=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketSections = jsonObject.getJSONObject("data")
        for (marketSectionKey in marketSections.keys()) {
            val marketSection = marketSections.getJSONArray(marketSectionKey)
            for (i in 0 until marketSection.length()) {
                val market = marketSection.getJSONObject(i)

                pairs.add(
                    CurrencyPairInfo(
                        market.getString("baseCurrency").uppercase(Locale.ROOT),
                        market.getString("quoteCurrency").uppercase(Locale.ROOT),
                        null
                    ))
            }
        }
    }

    override fun getPairId(checkerInfo: CheckerInfo): String {
        return (checkerInfo.currencyBase + checkerInfo.currencyCounter).lowercase(Locale.ROOT)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerData = jsonObject.getJSONObject("data").getJSONObject("trade_ticker_data")

        ticker.timestamp = tickerData.getLong("ts")
        tickerData.getJSONObject("tick").let {
            ticker.last = it.getDouble("close")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.vol = it.getDouble("vol")
        }
    }
}