package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class Coinbase : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Coinbase"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://api.pro.coinbase.com/products/%1\$s/ticker"
        private const val URL_STATS = "https://api.pro.coinbase.com/products/%1\$s/stats"
        private const val URL_CURRENCY_PAIRS = "https://api.pro.coinbase.com/products"

        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()
    }

    init {
        CURRENCY_PAIRS["1INCH"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["AAVE"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["ADA"] = arrayOf("BTC", "ETH", "EUR", "GBP", "USD", "USDC")
        CURRENCY_PAIRS["ATOM"] = arrayOf("BTC", "USD")
        CURRENCY_PAIRS["BAT"] = arrayOf("BTC", "ETH", "EUR", "USD", "USDC")
        CURRENCY_PAIRS["BTC"] = arrayOf("EUR", "GBP", "USDC", "USD", "USDT")
        CURRENCY_PAIRS["DAI"] = arrayOf("USD", "USDC")
        CURRENCY_PAIRS["DASH"] = arrayOf("BTC", "USD")
        CURRENCY_PAIRS["DOGE"] = arrayOf("BTC", "EUR", "GBP", "USD", "USDT")
        CURRENCY_PAIRS["DOT"] = arrayOf("BTC", "EUR", "GBP", "USD", "USDT")
        CURRENCY_PAIRS["EOS"] = arrayOf("BTC", "EUR", "USD")
        CURRENCY_PAIRS["ETC"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["ETH"] = arrayOf("BTC", "DAI", "EUR", "GBP", "USD", "USDT", "USDC")
        CURRENCY_PAIRS["FIL"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["LINK"] = arrayOf("BTC", "ETH", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["LTC"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["OMG"] = arrayOf("BTC", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["STORJ"] = arrayOf("BTC", "USD")
        CURRENCY_PAIRS["SUSHI"] = arrayOf("BTC", "ETH", "EUR", "GBP", "USD")
        CURRENCY_PAIRS["USDC"] = arrayOf("EUR", "GBP")
        CURRENCY_PAIRS["USDT"] = arrayOf("EUR", "GBP", "USD", "USDC")
        CURRENCY_PAIRS["XLM"] = arrayOf("BTC", "EUR", "USD")
        CURRENCY_PAIRS["ZEC"] = arrayOf("BTC", "USD", "USDC")
    }

    override fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 2
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val pairId = checkerInfo.currencyPairId ?: "${checkerInfo.currencyBase}-${checkerInfo.currencyCounter}"

        if(requestId == 0)
            return String.format(URL_TICKER, pairId)

        return String.format(URL_STATS, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        if(requestId == 0) {
            ticker.vol = jsonObject.getDouble("volume").also {
                if(it <= 0)
                    throw MarketParseException("No trading volume")
            }

            ticker.bid = jsonObject.getDouble("bid")
            ticker.ask = jsonObject.getDouble("ask")
            ticker.last = jsonObject.getDouble("price")
            ticker.timestamp = TimeUtils.convertISODateToTimestamp(jsonObject.getString("time"))
        }
        else {
            ticker.high = jsonObject.getDouble("high")
            ticker.low = jsonObject.getDouble("low")
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        JSONArray(responseString).forEachJSONObject { pairJsonObject ->
            pairs.add(CurrencyPairInfo(
                    pairJsonObject.getString("base_currency"),
                    pairJsonObject.getString("quote_currency"),
                    pairJsonObject.getString("id")))
        }
    }
}