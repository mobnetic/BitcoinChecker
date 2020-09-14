package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Cryptopia : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJsonObject = jsonObject.getJSONObject("Data")
        ticker.bid = dataJsonObject.getDouble("BidPrice")
        ticker.ask = dataJsonObject.getDouble("AskPrice")
        ticker.vol = dataJsonObject.getDouble("Volume")
        ticker.high = dataJsonObject.getDouble("High")
        ticker.low = dataJsonObject.getDouble("Low")
        ticker.last = dataJsonObject.getDouble("LastPrice")
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("Message")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonArray = jsonObject.getJSONArray("Data")
        for (i in 0 until dataJsonArray.length()) {
            val pairJsonObject = dataJsonArray.getJSONObject(i)
            val currencyBase = pairJsonObject.getString("Symbol")
            val currencyCounter = pairJsonObject.getString("BaseSymbol")
            val pairId = pairJsonObject.getString("Id")
            pairs.add(CurrencyPairInfo(
                    currencyBase,
                    currencyCounter,
                    pairId))
        }
    }

    companion object {
        private const val NAME = "Cryptopia"
        private const val TTS_NAME = "Cryptopia"
        private const val URL = "https://www.cryptopia.co.nz/api/GetMarket/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://www.cryptopia.co.nz/api/GetTradePairs"
    }
}