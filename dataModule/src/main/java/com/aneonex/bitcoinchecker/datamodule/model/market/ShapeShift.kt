package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import org.json.JSONObject
import java.util.*

class ShapeShift : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.last = ParseUtils.getDoubleFromString(jsonObject, "rate")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val jsonCoinNames = jsonObject.names()
        val availableCoinNames: MutableList<String> = ArrayList(jsonCoinNames.length())
        for (i in 0 until jsonCoinNames.length()) {
            val coinJsonObject = jsonObject.getJSONObject(jsonCoinNames.getString(i))
            if ("available" == coinJsonObject.optString("status")) {
                availableCoinNames.add(coinJsonObject.getString("symbol"))
            }
        }
        val coinesCount = availableCoinNames.size
        for (i in 0 until coinesCount) {
            for (j in 0 until coinesCount) {
                if (i != j) {
                    val currencyBase = availableCoinNames[i]
                    val currencyCounter = availableCoinNames[j]
                    pairs.add(CurrencyPairInfo(
                            currencyBase,
                            currencyCounter,
                            null))
                }
            }
        }
    }

    companion object {
        private const val NAME = "ShapeShift"
        private const val TTS_NAME = "Shape Shift"
        private const val URL = "https://shapeshift.io/rate/%1\$s_%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://shapeshift.io/getcoins"
    }
}