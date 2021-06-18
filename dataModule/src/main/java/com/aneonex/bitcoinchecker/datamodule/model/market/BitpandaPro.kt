package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONArray
import org.json.JSONObject

class BitpandaPro : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bitpanda Pro"
        private const val TTS_NAME = "Bit panda pro"
        private const val URL = "https://api.exchange.bitpanda.com/public/v1/market-ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.exchange.bitpanda.com/public/v1/market-ticker"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val marketsJson = JSONArray(responseString)
        for (i in 0 until marketsJson.length()) {
            val market = marketsJson.getJSONObject(i)

            val pairId = market.getString("instrument_code")
            val assets = pairId.split('_')

            if(assets.size != 2)
                continue

            pairs.add(
                CurrencyPairInfo(
                    assets[0], // Base
                    assets[1], // Quoting
                    pairId
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.bid = getDouble("best_bid")
            ticker.ask = getDouble("best_ask")
            ticker.high = getDouble("high")
            ticker.low = getDouble("low")
            ticker.last = getDouble("last_price")
            ticker.vol = getDouble("base_volume")
            ticker.timestamp = TimeUtils.convertISODateToTimestamp(getString("time"))
        }
    }
}