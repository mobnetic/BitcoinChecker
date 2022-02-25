package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class WazirX : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "WazirX"
        private const val TTS_NAME = "Wazir X"
        private const val URL = "https://api.wazirx.com/api/v2/tickers/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.wazirx.com/api/v2/market-status"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = jsonObject.getJSONArray("markets")
        for (i in 0 until marketsJson.length()) {
            val market = marketsJson.getJSONObject(i)

            val baseCurrency = market.getString("baseMarket")
            val qouteCurrency = market.getString("quoteMarket")
            val pairId = "$baseCurrency$qouteCurrency"

            pairs.add(
                CurrencyPairInfo(
                    baseCurrency.uppercase(Locale.ROOT),
                    qouteCurrency.uppercase(Locale.ROOT),
                    pairId
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            getJSONObject("ticker").apply {
                ticker.bid = getDouble("buy")
                ticker.ask = getDouble("sell")
                ticker.high = getDouble("high")
                ticker.low = getDouble("low")
                ticker.last = getDouble("last")
                ticker.vol = getDouble("vol")
            }
            ticker.timestamp = getLong("at")
        }
    }
}