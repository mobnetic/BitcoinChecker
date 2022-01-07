package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Coinsbit : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Coinsbit"
        private const val TTS_NAME = NAME
        private const val URL = "https://coinsbit.io/api/v1/public/ticker?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://coinsbit.io/api/v1/public/markets"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("result")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)
            pairs.add( CurrencyPairInfo(
                    market.getString("stock"),
                    market.getString("money"),
                    market.getString("name"),
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("result").apply {
            ticker.bid = getDouble("bid")
            ticker.ask = getDouble("ask")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.vol = getDouble("volume")
//            ticker.timestamp = getLong("timestamp")
        }
    }
}