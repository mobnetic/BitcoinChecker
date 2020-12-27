package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray

class HitBtc : SimpleMarket(
        "HitBTC",
        "https://api.hitbtc.com/api/2/public/symbol",
        "https://api.hitbtc.com/api/2/public/ticker?symbols=%1\$s",
        "Hit BTC") {

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val symbols = JSONArray(responseString)
        for (i in 0 until symbols.length()) {
            val market = symbols.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    market.getString("baseCurrency"),
                    market.getString("quoteCurrency"),
                    market.getString("id")))
        }
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickers = JSONArray(responseString)
        if(tickers.length() == 0) throw Exception("No data")
        tickers.getJSONObject(0).apply {
            ticker.bid = getDouble("bid")
            ticker.ask = getDouble("ask")
            ticker.vol = getDouble("volume")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last")
            ticker.timestamp = TimeUtils.convertISODateToTimestamp(getString("timestamp"))
        }
    }
}