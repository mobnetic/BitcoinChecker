package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

class Btcturk : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BtcTurk"
        private const val TTS_NAME = "Btc Turk"
        private const val URL = "https://api.btcturk.com/api/v2/ticker?pairSymbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.btcturk.com/api/v2/ticker"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.TRY
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.TRY
            )
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val pairsArray = JSONObject(responseString).getJSONArray("data")

        for (i in 0 until pairsArray.length()) {
            val pairJson = pairsArray.getJSONObject(i)
            pairs.add(CurrencyPairInfo(
                    pairJson.getString("numeratorSymbol"),
                    pairJson.getString("denominatorSymbol"),
                    pairJson.getString("pairNormalized")
                )
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if (pairId == null) {
            pairId = "${checkerInfo.currencyBase}_${checkerInfo.currencyCounter}";
        }

        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJson = JSONObject(responseString)
        val dataJsonArray =  tickerJson.getJSONArray("data")

        dataJsonArray.getJSONObject(0).let{
            ticker.bid = it.getDouble("bid")
            ticker.ask = it.getDouble("ask")
            ticker.vol = it.getDouble("volume")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("last")
            ticker.timestamp = (it.getDouble("timestamp") * TimeUtils.MILLIS_IN_SECOND).toLong()
        }
    }
}