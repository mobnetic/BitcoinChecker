package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Mxc : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "MXC"
        private const val TTS_NAME = NAME
        private const val URL = "https://www.mxc.com/open/api/v1/data/ticker?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://www.mxc.com/open/api/v1/data/markets"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")
        for(i in 0 until markets.length()){
            val pairId = markets.getString(i)

            // Split pairs like BTC_USDT
            val assets = pairId.split('_')

            if(assets.size != 2)
                continue

            pairs.add( CurrencyPairInfo(
                    assets[0].toUpperCase(Locale.ROOT),
                    assets[1].toUpperCase(Locale.ROOT),
                    pairId,
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").apply {
            ticker.ask = getDouble("sell")
            ticker.bid = getDouble("buy")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.vol = getDouble("volume")
//            ticker.timestamp = getLong("ts")
        }
    }
}