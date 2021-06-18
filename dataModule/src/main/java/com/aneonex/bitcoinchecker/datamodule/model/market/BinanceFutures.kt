package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class BinanceFutures : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Binance Futures"
        private const val TTS_NAME = NAME
        private const val URL = "https://fapi.binance.com/fapi/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://fapi.binance.com/fapi/v1/exchangeInfo"
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.vol = jsonObject.getDouble("volume")
        ticker.high = jsonObject.getDouble("highPrice")
        ticker.low = jsonObject.getDouble("lowPrice")
        ticker.last = jsonObject.getDouble("lastPrice")
        ticker.timestamp = jsonObject.getLong("closeTime")
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("symbols").forEachJSONObject { marketJsonObject ->
            // Tha app UI supports only perpetual futures
            if(marketJsonObject.getString("contractType") != "PERPETUAL")
                return@forEachJSONObject

            val symbol = marketJsonObject.getString("symbol")
            val baseAsset = marketJsonObject.getString("baseAsset")
            val quoteAsset = marketJsonObject.getString("quoteAsset")

            pairs.add(CurrencyPairInfo(
                    baseAsset,
                    quoteAsset,
                    symbol))
        }
    }
}