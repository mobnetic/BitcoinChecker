package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class CoinEx : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "CoinEx"
        private const val TTS_NAME = "Coin ex"
        private const val URL = "https://api.coinex.com/v1/market/ticker?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.coinex.com/v1/market/info"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = jsonObject.getJSONObject("data")

        marketsJson.keys().forEach {
            val market = marketsJson.getJSONObject(it)
            pairs.add( CurrencyPairInfo(
                    market.getString("trading_name"),
                    market.getString("pricing_name"),
                    market.getString("name"),
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJson = jsonObject.getJSONObject("data")
        ticker.timestamp = dataJson.getLong("date")

        dataJson.getJSONObject("ticker").apply {
            ticker.bid = getDouble("buy")
            ticker.ask = getDouble("sell")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.vol = getDouble("vol")
        }
    }
}