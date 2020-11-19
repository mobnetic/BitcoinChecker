package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Mexo : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Mexo"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.mexo.io/openapi/quote/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.mexo.io/openapi/v1/brokerInfo"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("symbols")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            pairs.add( CurrencyPairInfo(
                    market.getString("baseAsset"),
                    market.getString("quoteAsset"),
                    market.getString("symbol")
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.ask = getDouble("bestAskPrice")
            ticker.bid = getDouble("bestBidPrice")
            ticker.high = getDouble("highPrice")
            ticker.low = getDouble("lowPrice")
            ticker.last = getDouble("lastPrice")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}