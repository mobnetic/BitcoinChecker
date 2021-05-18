package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Coineal : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Coineal"
        private const val TTS_NAME = NAME
        private const val URL = "https://exchange-open-api.coineal.com/open/api/get_ticker?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://exchange-open-api.coineal.com/open/api/common/symbols"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)
            pairs.add(
                CurrencyPairInfo(
                    market.getString("base_coin").uppercase(Locale.ROOT),
                    market.getString("count_coin").uppercase(Locale.ROOT),
                    market.getString("symbol"),
                ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").apply {
            ticker.bid = getDouble("buy")
            ticker.ask = getDouble("sell")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
//            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}