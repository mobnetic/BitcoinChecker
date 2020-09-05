package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.ParseUtils
import com.aneonex.bitcoinchecker.datamodule.R
import org.json.JSONObject

class AllCoin : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJsonObject = jsonObject.getJSONObject("data")
        ticker.bid = ParseUtils.getDoubleFromString(dataJsonObject, "top_bid")
        ticker.ask = ParseUtils.getDoubleFromString(dataJsonObject, "top_ask")
        ticker.vol = ParseUtils.getDoubleFromString(dataJsonObject, "volume_24h_" + checkerInfo.currencyBase)
        ticker.high = ParseUtils.getDoubleFromString(dataJsonObject, "max_24h_price")
        ticker.low = ParseUtils.getDoubleFromString(dataJsonObject, "min_24h_price")
        ticker.last = ParseUtils.getDoubleFromString(dataJsonObject, "trade_price")
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("error_info")
    }

    override val cautionResId: Int
        get() = R.string.market_caution_allcoin

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonObject = jsonObject.getJSONObject("data")
        val pairsJsonArray = dataJsonObject.names()
        for (i in 0 until pairsJsonArray.length()) {
            val pairName = pairsJsonArray.getString(i)
            val marketJsonObject = dataJsonObject.getJSONObject(pairName)
            pairs.add(CurrencyPairInfo(
                    marketJsonObject.getString("type"),
                    marketJsonObject.getString("exchange"),
                    pairName))
        }
    }

    companion object {
        private const val NAME = "AllCoin"
        private const val TTS_NAME = "All Coin"
        private const val URL = "https://www.allcoin.com/api2/pair/%1\$s_%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://www.allcoin.com/api2/pairs"
    }
}