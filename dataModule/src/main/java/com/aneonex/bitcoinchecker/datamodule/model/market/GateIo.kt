package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray

class GateIo : Market(NAME, TTS_NAME, null) {

    companion object {
        private const val NAME = "Gate.io"
        private const val TTS_NAME = "Gate io"
        private const val URL = "https://api.gateio.ws/api/v4/spot/tickers?currency_pair=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.gateio.ws/api/v4/spot/currency_pairs"
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val jsonArray = JSONArray(responseString)
        if(jsonArray.length() < 1) throw MarketParseException("No data")

        val jsonObject = jsonArray.getJSONObject(0)

        ticker.bid = jsonObject.getDouble("highest_bid")
        ticker.ask = jsonObject.getDouble("lowest_ask")
        ticker.vol = jsonObject.getDouble("base_volume")
        ticker.high = jsonObject.getDouble("high_24h")
        ticker.low = jsonObject.getDouble("low_24h")
        ticker.last = jsonObject.getDouble("last")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val jsonArray = JSONArray(responseString)

        for (i in 0 until jsonArray.length()) {
            val pairJson = jsonArray.getJSONObject(i)

            if(pairJson.getString("trade_status") != "tradable") continue

            pairs.add(CurrencyPairInfo(
                    pairJson.getString("base"),
                    pairJson.getString("quote"),
                    pairJson.getString("id")
            ))
        }
    }
}