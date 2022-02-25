package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONArray
import org.json.JSONObject


class CapeCrypto : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Cape Crypto"
        private const val TTS_NAME = NAME
        // private const val URL_TICKER = "https://trade.capecrypto.com/api/v2/peatio/public/markets/btczar/tickers"
        private const val URL_TICKER = "https://trade.capecrypto.com/api/v2/peatio/public/markets/%1\$s%2\$s/tickers"
        private const val URL_ORDERS = "https://trade.capecrypto.com/api/v2/peatio/public/markets/%1\$s%2\$s/order-book?asks_limit=1&bids_limit=1"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.ZAR
            )
        }
    }

    override fun getNumOfRequests(checkerInfo: CheckerInfo?): Int {
        return 2
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return if (requestId == 0) {
            String.format(URL_TICKER, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
        } else {
            String.format(URL_ORDERS, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker,
                                           checkerInfo: CheckerInfo) {

        if (requestId == 0) {
            ticker.vol = jsonObject.getJSONObject("ticker").getDouble("vol")
            ticker.high = jsonObject.getJSONObject("ticker").getDouble("high")
            ticker.low = jsonObject.getJSONObject("ticker").getDouble("low")
            ticker.last = jsonObject.getJSONObject("ticker").getDouble("last")
            ticker.timestamp = jsonObject.getLong("at")
        } else {
            val jArrayBids: JSONArray = jsonObject.getJSONArray("bids")
            val jArrayAsks: JSONArray = jsonObject.getJSONArray("asks")
            val jResultBids = jArrayBids.getJSONObject(0)
            val jResultAsks = jArrayAsks.getJSONObject(0)
            ticker.bid = jResultBids.getDouble("price")
            ticker.ask = jResultAsks.getDouble("price")
        }
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject,
                                          checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("message")
    }
}
