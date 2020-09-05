package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Comkort : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val marketsJsonObject = jsonObject.getJSONObject("markets")
        val marketNames = marketsJsonObject.names()
        val marketJsonObject = marketsJsonObject.getJSONObject(marketNames.getString(0))
        ticker.bid = getFirstOrderFrom(marketJsonObject, "buy_orders")
        ticker.ask = getFirstOrderFrom(marketJsonObject, "sell_orders")
        ticker.vol = marketJsonObject.getDouble("volume")
        ticker.high = marketJsonObject.getDouble("high")
        ticker.low = marketJsonObject.getDouble("low")
        ticker.last = marketJsonObject.getDouble("last_price")
    }

    @Throws(Exception::class)
    private fun getFirstOrderFrom(marketJsonObject: JSONObject, arrayName: String): Double {
        val ordersJsonArray = marketJsonObject.getJSONArray(arrayName)
        return if (ordersJsonArray.length() > 0) {
            ordersJsonArray.getJSONObject(0).getDouble("price")
        } else Ticker.NO_DATA.toDouble()
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJsonArray = jsonObject.getJSONArray("markets")
        for (i in 0 until marketsJsonArray.length()) {
            val marketJsonObject = marketsJsonArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    marketJsonObject.getString("item"),
                    marketJsonObject.getString("price_currency"),
                    marketJsonObject.getString("alias")))
        }
    }

    companion object {
        private const val NAME = "Comkort"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.comkort.com/v1/public/market/summary?market_alias=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.comkort.com/v1/public/market/list"
    }
}