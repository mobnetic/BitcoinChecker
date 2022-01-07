package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class LocalBitcoins : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairJsonObject = jsonObject.getJSONObject(checkerInfo.currencyPairId!!)
        ticker.vol = pairJsonObject.getDouble("volume_btc")
        val ratesJsonObject = pairJsonObject.getJSONObject("rates")
        ticker.last = ratesJsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairsJsonArray = jsonObject.names()!!
        for (i in 0 until pairsJsonArray.length()) {
            val currencyCounter = pairsJsonArray.getString(i)
            if (currencyCounter != null) {
                pairs.add(CurrencyPairInfo(
                        VirtualCurrency.BTC,
                        currencyCounter,
                        currencyCounter))
            }
        }
    }

    companion object {
        private const val NAME = "LocalBitcoins"
        private const val TTS_NAME = "Local Bitcoins"
        private const val URL = "https://localbitcoins.com/bitcoinaverage/ticker-all-currencies/"
        private const val URL_CURRENCY_PAIRS = URL
    }
}