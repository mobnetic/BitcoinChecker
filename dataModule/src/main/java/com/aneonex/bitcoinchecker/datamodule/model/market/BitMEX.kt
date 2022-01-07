package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class BitMEX : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitMEX"
        private const val TTS_NAME = NAME
        private const val URL = "https://www.bitmex.com/api/v1/instrument" +
                "?symbol=%1\$s" +
                "&columns=bidPrice,askPrice,turnover24h,highPrice,lowPrice,lastPrice"
        private const val URL_CURRENCY_PAIRS = "https://www.bitmex.com/api/v1/instrument" +
                "?columns=rootSymbol,typ" +
                "&filter={\"state\":\"Open\"}"

        private fun getDisplaySymbol(internalSymbol: String): String {
            return when(internalSymbol) {
                "XBT" -> "BTC"
                else -> internalSymbol
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        parseTickerFromJsonObject(requestId, JSONArray(responseString).getJSONObject(0), ticker, checkerInfo)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bidPrice")
        ticker.ask = jsonObject.getDouble("askPrice")
        // This comes back in Satoshis
        ticker.vol = jsonObject.getDouble("turnover24h") / 1e8
        if (!jsonObject.isNull("highPrice")) ticker.high = jsonObject.getDouble("highPrice")
        if (!jsonObject.isNull("lowPrice")) ticker.low = jsonObject.getDouble("lowPrice")
        ticker.last = jsonObject.getDouble("lastPrice")
        // This is an ISO timestamp representing UTC time
        ticker.timestamp = TimeUtils.convertISODateToTimestamp(jsonObject.getString("timestamp"))
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        JSONArray(responseString).forEachJSONObject { instrument ->
            parseCurrencyPairsFromJsonObject(requestId, instrument, pairs)
        }
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        var base = jsonObject.getString("rootSymbol")
        val id = jsonObject.getString("symbol")
        var quote = id.substring(id.indexOf(base) + base.length)

        // Binary
        if (jsonObject.getString("typ") == "FFICSX") {
            quote = base
            base = "BINARY"
        }
        pairs.add(CurrencyPairInfo(getDisplaySymbol(base), getDisplaySymbol(quote), id))
    }
}