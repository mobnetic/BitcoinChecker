package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class BitBay : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitBay.net"
        private const val TTS_NAME = "Bit Bay"
        private const val URL = "https://bitbay.net/API/Public/%1\$s%2\$s/ticker.json"
        private const val URL_CURRENCY_PAIRS = "https://api.bitbay.net/rest/trading/ticker"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val itemsJson = jsonObject.getJSONObject("items")
        itemsJson.keys().forEach {
            val coins = it.split('-')
            if(coins.size == 2){
                pairs.add( CurrencyPairInfo(
                        coins[0], // base
                        coins[1], // quote
                        null
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume")
        if(jsonObject.has("max"))
            ticker.high = jsonObject.getDouble("max")
        if(jsonObject.has("min"))
            ticker.low = jsonObject.getDouble("min")
        ticker.last = jsonObject.getDouble("last")
    }
}