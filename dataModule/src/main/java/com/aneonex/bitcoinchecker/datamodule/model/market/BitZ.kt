package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class BitZ : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitZ"
        private const val TTS_NAME = NAME
        private const val URL = "https://apiv2.bitz.com/Market/ticker?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://apiv2.bitz.com/Market/symbolList"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = jsonObject.getJSONObject("data")

        marketsJson.keys().forEach {
            val market = marketsJson.getJSONObject(it)
            pairs.add( CurrencyPairInfo(
                    market.getString("coinFrom").toUpperCase(Locale.ROOT),
                    market.getString("coinTo").toUpperCase(Locale.ROOT),
                    market.getString("name"),
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").apply {
            ticker.ask = getDouble("askPrice")
            ticker.bid = getDouble("bidPrice")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("now")
            ticker.vol = getDouble("volume")
        }
        ticker.timestamp = jsonObject.getLong("time")
    }
}