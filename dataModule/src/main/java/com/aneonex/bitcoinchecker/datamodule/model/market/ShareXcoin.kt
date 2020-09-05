package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class ShareXcoin : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume")
        ticker.high = jsonObject.getDouble("high")
        ticker.low = jsonObject.getDouble("low")
        ticker.last = jsonObject.getDouble("last")
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
                    marketJsonObject.getString("coin1"),
                    marketJsonObject.getString("coin2"),
                    marketJsonObject.getString("market_id")))
        }
    }

    companion object {
        private const val NAME = "ShareXcoin"
        private const val TTS_NAME = "Share X coin"
        private const val URL = "https://sharexcoin.com/public_api/v1/market/%1\$s_%2\$s/summary"
        private const val URL_CURRENCY_PAIRS = "https://sharexcoin.com/public_api/v1/market/summary"
    }
}