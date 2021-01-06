package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONArray
import org.json.JSONObject

class Bitstamp : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Bitstamp"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://www.bitstamp.net/api/v2/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://www.bitstamp.net/api/v2/trading-pairs-info/"

        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[Currency.EUR] = arrayOf(
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[Currency.GBP] = arrayOf(
                    Currency.EUR,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.XLM] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.EUR,
                    Currency.USD,
                    Currency.GBP
            )
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)

        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            if(market.getString("trading") == "Enabled") {
                val assetsInPair = market.getString("name").split('/')
                if (assetsInPair.size != 2) continue

                pairs.add(CurrencyPairInfo(
                        assetsInPair[0], // base
                        assetsInPair[1], // quote
                        market.getString("url_symbol")
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        // Compatibility with old pairs
        val pairId = checkerInfo.currencyPairId ?: (checkerInfo.currencyBaseLowerCase + checkerInfo.currencyCounterLowerCase)
        return String.format(URL_TICKER, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume")
        ticker.high = jsonObject.getDouble("high")
        ticker.low = jsonObject.getDouble("low")
        ticker.last = jsonObject.getDouble("last")
        ticker.timestamp = jsonObject.getLong("timestamp")
    }
}