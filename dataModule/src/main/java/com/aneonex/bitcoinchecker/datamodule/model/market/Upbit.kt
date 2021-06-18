package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray

class Upbit : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Upbit"
        private const val TTS_NAME = "Up bit"
        private const val URL = "https://api.upbit.com/v1/ticker?markets=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.upbit.com/v1/market/all?isDetails=false"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)

        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            val marketId = market.getString("market")
            val assets = marketId.split("-")
            if(assets.size != 2) continue

            pairs.add( CurrencyPairInfo(
                    assets[1],
                    assets[0],
                    marketId,
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val jsonArray = JSONArray(responseString)

        jsonArray.getJSONObject(0).apply {
            ticker.high = getDouble("high_price")
            ticker.low = getDouble("low_price")
            ticker.last = getDouble("trade_price")
            ticker.vol = getDouble("acc_trade_volume")
            ticker.timestamp = getLong("timestamp")
        }
    }
}