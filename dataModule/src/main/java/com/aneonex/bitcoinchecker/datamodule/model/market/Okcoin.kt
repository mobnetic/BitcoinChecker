package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONArray
import org.json.JSONObject

class Okcoin : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "OKCoin"
        private const val TTS_NAME = "OK Coin"
        private const val URL = "https://www.okcoin.com/api/spot/v3/instruments/%1\$s/ticker"
        private const val URL_CURRENCY_PAIRS = "https://www.okcoin.com/api/spot/v3/instruments"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR
            )
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val pairsArray = JSONArray(responseString)
        for (i in 0 until pairsArray.length()) {
            val pairJson = pairsArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairJson.getString("base_currency"),
                    pairJson.getString("quote_currency"),
                    pairJson.getString("instrument_id")))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if(pairId == null){
            pairId = "${checkerInfo.currencyBase}-${checkerInfo.currencyCounter}"
        }
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
//        val tickerJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("base_volume_24h")
        ticker.high = jsonObject.getDouble("high_24h")
        ticker.low = jsonObject.getDouble("low_24h")
        ticker.last = jsonObject.getDouble("last")
    }
}