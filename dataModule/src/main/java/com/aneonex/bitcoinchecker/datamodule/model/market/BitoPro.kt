package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class BitoPro : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitoPro"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bitopro.com/v3/tickers/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bitopro.com/v3/provisioning/trading-pairs"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJson = jsonObject.getJSONArray("data")
        for(i in 0 until dataJson.length()){
            val pairJson = dataJson.getJSONObject(i)
//            if(!pairJson.getBoolean("maintain")) continue
            pairs.add(
                CurrencyPairInfo(
                    pairJson.getString("base").uppercase(Locale.ROOT),
                    pairJson.getString("quote").uppercase(Locale.ROOT),
                    pairJson.getString("pair")
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJson = jsonObject.getJSONObject("data")
        ticker.vol = dataJson.getDouble("volume24hr")
        ticker.high = dataJson.getDouble("high24hr")
        ticker.low = dataJson.getDouble("low24hr")
        ticker.last = dataJson.getDouble("lastPrice")
    }
}