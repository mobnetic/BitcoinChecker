package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Bybit : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bybit"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bybit.com/v2/public/tickers?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bybit.com/v2/public/symbols"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("result")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            val instrumentName = market.getString("name")
            val quoteCurrency = market.getString("quote_currency")
            var baseCurrency = market.getString("base_currency")

            // Detect futures
            if(!instrumentName.endsWith(quoteCurrency)) {
                baseCurrency = market.getString("alias").replace(quoteCurrency, "-")
            }

            pairs.add( CurrencyPairInfo(
                    baseCurrency,
                    quoteCurrency,
                    instrumentName
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONArray("result").getJSONObject(0).apply {
            ticker.ask = getDouble("ask_price")
            ticker.bid = getDouble("bid_price")
            ticker.high = getDouble("high_price_24h")
            ticker.low = getDouble("low_price_24h")
            ticker.last = getDouble("last_price")
            ticker.vol = getDouble("turnover_24h")
//            ticker.timestamp = getLong("ts")
        }
    }
}