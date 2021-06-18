package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.forEachString
import org.json.JSONObject

class CoinDesk : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "CoinDesk"
        private const val TTS_NAME = "Coin Desk"
        private const val URL = "https://api.coindesk.com/v1/bpi/currentprice.json"
        private const val URL_CURRENCY_PAIRS = URL
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val bpiJsonObject = jsonObject.getJSONObject("bpi")
        val pairJsonObject = bpiJsonObject.getJSONObject(checkerInfo.currencyCounter)
        ticker.last = pairJsonObject.getDouble("rate_float")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val bpiJsonObject = jsonObject.getJSONObject("bpi")
        bpiJsonObject.names()!!.forEachString { quoteCurrencyName ->
            pairs.add(CurrencyPairInfo(VirtualCurrency.BTC, quoteCurrencyName, null))
        }
    }
}