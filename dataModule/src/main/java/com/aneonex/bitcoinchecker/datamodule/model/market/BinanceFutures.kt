package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class BinanceFutures : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Binance Futures"
        private const val TTS_NAME = NAME

        private const val URL_USD_M = "https://fapi.binance.com/fapi/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS_USD_M = "https://fapi.binance.com/fapi/v1/exchangeInfo"

        private const val URL_COIN_M = "https://dapi.binance.com/dapi/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS_COIN_M = "https://dapi.binance.com/dapi/v1/exchangeInfo"

        private const val COIN_M_PREFIX = "2:"

        private fun isCoinMPair(pairId: String) = pairId.startsWith(COIN_M_PREFIX)

        private fun parseTicker(jsonObject: JSONObject, ticker: Ticker) {
            jsonObject.apply {
                ticker.vol = getDouble("volume")
                ticker.high = getDouble("highPrice")
                ticker.low = getDouble("lowPrice")
                ticker.last = getDouble("lastPrice")
                ticker.timestamp = getLong("closeTime")

                // Optional
                ticker.volQuote = optDouble("quoteVolume", ticker.volQuote)
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return if(isCoinMPair(checkerInfo.currencyPairId!!)) {
            val pairId = checkerInfo.currencyPairId.substring(COIN_M_PREFIX.length)
            String.format(URL_COIN_M, pairId)
        }
        else {
            String.format(URL_USD_M, checkerInfo.currencyPairId)
        }
    }

    override fun parseTicker(
        requestId: Int,
        responseString: String,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        if(isCoinMPair(checkerInfo.currencyPairId!!))
            parseTicker(JSONArray(responseString).getJSONObject(0), ticker)
        else
            parseTicker(JSONObject(responseString), ticker)
    }

    override val currencyPairsNumOfRequests: Int
        get() = 2

    override fun getCurrencyPairsUrl(requestId: Int): String =
        if(requestId == 0) URL_CURRENCY_PAIRS_USD_M else URL_CURRENCY_PAIRS_COIN_M

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("symbols").forEachJSONObject { marketJsonObject ->
            // Tha app UI supports only perpetual futures
            if(marketJsonObject.getString("contractType") != "PERPETUAL")
                return@forEachJSONObject

            val symbol = marketJsonObject.getString("symbol").let {
                if(requestId > 0) COIN_M_PREFIX + it else it
            }
            val baseAsset = marketJsonObject.getString("baseAsset")
            val quoteAsset = marketJsonObject.getString("quoteAsset")

            pairs.add(CurrencyPairInfo(
                    baseAsset,
                    quoteAsset,
                    symbol))
        }
    }
}