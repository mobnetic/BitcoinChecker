package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Bitmax : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bitmax"
        private const val TTS_NAME = NAME
        private const val URL = "https://bitmax.io/api/pro/v1/ticker?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://bitmax.io/api/pro/v1/products"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)
            if(market.getString("status") == "Normal") {
                pairs.add(CurrencyPairInfo(
                        market.getString("baseAsset"),
                        market.getString("quoteAsset"),
                        market.getString("symbol")
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").let {
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("close")
            ticker.vol = it.getDouble("volume")

            it.getJSONArray("bid").let { bids ->
                if(bids.length() == 2) ticker.bid = bids.getDouble(0)
            }
            it.getJSONArray("ask").let { asks ->
                if(asks.length() == 2) ticker.ask = asks.getDouble(0)
            }
        }
    }
}