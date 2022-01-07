package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONArray
import com.aneonex.bitcoinchecker.datamodule.util.forEachString
import org.json.JSONArray
import java.util.*

class Bitfinex : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bitfinex"
        private const val TTS_NAME = NAME
        private const val URL = "https://api-pub.bitfinex.com/v2/ticker/%1\$s"
        private const val URL_CURRENCY_SYMBOLS = "https://api-pub.bitfinex.com/v2/conf/pub:map:currency:sym"
        private const val URL_CURRENCY_PAIRS = "https://api-pub.bitfinex.com/v2/conf/pub:list:pair:exchange"
    }

    private val symbolsMap = mutableMapOf<String, String>()

    override val currencyPairsNumOfRequests: Int
        get() = 2 // 1) symbol map, 2) pairs

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if (pairId == null) {
            pairId = String.format(
                "t%1\$s%2\$s",
                checkerInfo.currencyBase.uppercase(Locale.ROOT),
                checkerInfo.currencyCounter.uppercase(Locale.ROOT)
            )
        }
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        // Ticker array format: https://docs.bitfinex.com/reference#rest-public-ticker
        // [ BID, BID_SIZE, ASK, ASK_SIZE, DAILY_CHANGE, DAILY_CHANGE_RELATIVE, LAST_PRICE, VOLUME, HIGH, LOW ],

        val jsonArray = JSONArray(responseString)
        ticker.bid = jsonArray.getDouble(0)
        ticker.ask = jsonArray.getDouble(2)
        ticker.last = jsonArray.getDouble(6)
        ticker.vol = jsonArray.getDouble(7)
        ticker.high = jsonArray.getDouble(8)
        ticker.low = jsonArray.getDouble(9)
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String =
        if (requestId == 0) URL_CURRENCY_SYMBOLS else URL_CURRENCY_PAIRS

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val dataArray = JSONArray(responseString)

        // Get symbols map
        if(requestId == 0) {
            symbolsMap.clear()

            if(dataArray.length() > 0){
                dataArray.getJSONArray(0).also { currencyArray ->
                    currencyArray.forEachJSONArray{ codeToSymbol ->
                        symbolsMap[codeToSymbol.getString(0)] = codeToSymbol.getString(1)
                    }
                }
            }
        }
        // Get pairs
        else {
            fun getCurrencyDisplayName(currencyCode: String) = symbolsMap[currencyCode] ?: currencyCode

            val pairsArray = dataArray.getJSONArray(0)
            pairsArray.forEachString { pairId ->
                val currencyBase: String
                val currencyCounter: String

                val splitPair = pairId.split(':')
                if (splitPair.size == 2) {
                    // pairId example "LINK:USD"
                    currencyBase = getCurrencyDisplayName(splitPair[0])
                    currencyCounter = getCurrencyDisplayName(splitPair[1])
                } else {
                    if (pairId.length != 6) return@forEachString
                    // pairId example "BTCUSD"
                    currencyBase = getCurrencyDisplayName(pairId.substring(0, 3))
                    currencyCounter = getCurrencyDisplayName(pairId.substring(3))
                }

                pairs.add(
                    CurrencyPairInfo(
                        currencyBase,
                        currencyCounter,
                        "t$pairId"
                    )
                )
            }

            // Clear cache
            symbolsMap.clear()
        }
    }
}