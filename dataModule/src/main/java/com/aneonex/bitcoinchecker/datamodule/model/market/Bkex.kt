package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Bkex : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BKEX"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bkex.io/v2/q/tickers?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bkex.io/v2/common/symbols"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            if(!market.getBoolean("supportTrade"))
                continue

            val pairId = market.getString("symbol")

            // Split pairs like BTC_USDT
            val assets = pairId.split('_')

            if(assets.size != 2)
                continue

            pairs.add( CurrencyPairInfo(
                    assets[0],
                    assets[1],
                    pairId,
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONArray("data").getJSONObject(0).apply {
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("close")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("ts")
        }
    }
}