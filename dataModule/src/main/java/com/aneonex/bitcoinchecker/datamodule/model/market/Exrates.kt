package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Exrates : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Exrates"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.exrates.me/v1/public/ticker?pair=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.exrates.me/v1/public/symbols"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJson = jsonObject.getJSONArray("data")
        for(i in 0 until dataJson.length()){
            val market = dataJson.getJSONObject(i)
            pairs.add( CurrencyPairInfo(
                    market.getString("base"),
                    market.getString("quote"),
                    market.getString("pair"),
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").apply {
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.vol = getDouble("volume_24H")
        }

        if(ticker.last <= 0) throw MarketParseException("No data")
    }
}