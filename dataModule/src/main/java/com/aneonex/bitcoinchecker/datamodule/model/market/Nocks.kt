package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Nocks : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataObject = jsonObject.getJSONObject("data")
        ticker.bid = getDoubleFromJsonObject(dataObject, "buy")
        ticker.ask = getDoubleFromJsonObject(dataObject, "sell")
        ticker.vol = getDoubleFromJsonObject(dataObject, "volume")
        ticker.high = getDoubleFromJsonObject(dataObject, "high")
        ticker.low = getDoubleFromJsonObject(dataObject, "low")
        ticker.last = getDoubleFromJsonObject(dataObject, "last")
    }

    @Throws(Exception::class)
    private fun getDoubleFromJsonObject(jsonObject: JSONObject, key: String): Double {
        return jsonObject.getJSONObject(key).getDouble("amount")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val resultJsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until resultJsonArray.length()) {
            val marketJsonObject = resultJsonArray.getJSONObject(i)
            val pairId = marketJsonObject.getString("code")
            val currencies = pairId.split("-".toRegex()).toTypedArray()
            if (currencies.size >= 2) {
                pairs.add(CurrencyPairInfo(
                        currencies[0],
                        currencies[1],
                        pairId))
            }
        }
    }

    companion object {
        private const val NAME = "Nocks"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.nocks.com/api/v2/trade-market/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.nocks.com/api/v2/trade-market"
    }
}