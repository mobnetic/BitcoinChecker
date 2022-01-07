package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class ZgCom : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "ZG.com"
        private const val TTS_NAME = "ZG dot com"
        private const val URL = "https://api.zg.com/openapi/quote/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.zg.com/openapi/v1/brokerInfo"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("symbols").forEachJSONObject { market ->
            if(market.getString("status") != "TRADING") return@forEachJSONObject

            pairs.add( CurrencyPairInfo(
                    market.getString("baseAsset"),
                    market.getString("quoteAsset"),
                    market.getString("symbol"),
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.bid = getDouble("bestBidPrice")
            ticker.ask = getDouble("bestAskPrice")
            ticker.high = getDouble("highPrice")
            ticker.low = getDouble("lowPrice")
            ticker.last = getDouble("lastPrice")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}