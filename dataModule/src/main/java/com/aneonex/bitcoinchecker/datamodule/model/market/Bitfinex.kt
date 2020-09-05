package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONArray
import java.util.*

class Bitfinex : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Bitfinex"
        private const val TTS_NAME = NAME
        private const val URL = "https://api-pub.bitfinex.com/v2/ticker/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api-pub.bitfinex.com/v2/tickers?symbols=ALL"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BCC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.ETH,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.BCU] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.DSH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.EOS] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.ETH,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.ETC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.IOT] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.ETH,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.OMG] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.ETH,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.RRT] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.SAN] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.ETH,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.XMR] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.ZEC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.USD
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if (pairId == null) {
            pairId = String.format("t%1\$s%2\$s",
                    checkerInfo.currencyBase.toUpperCase(Locale.ROOT),
                    checkerInfo.currencyCounter.toUpperCase(Locale.ROOT))
        }
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
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
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val pairsArray = JSONArray(responseString)
        for (i in 0 until pairsArray.length()) {
            val pairArray = pairsArray.getJSONArray(i)
            val pairId = pairArray.getString(0)
            if (pairId != null && pairId.length == 7) {
                // pairId example "tBTCUSD"
                pairs.add(CurrencyPairInfo(
                        pairId.substring(1, 4).toUpperCase(Locale.US),
                        pairId.substring(4).toUpperCase(Locale.US),
                        pairId))
            }
        }
    }
}