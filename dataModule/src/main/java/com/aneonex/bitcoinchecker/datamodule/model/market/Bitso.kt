package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject
import java.util.*

class Bitso : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Bitso"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.bitso.com/v3/ticker?book=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bitso.com/v3/available_books/"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val books = jsonObject.getJSONArray("payload")
        for (i in 0 until books.length()) {
            val pairId = books.getJSONObject(i).getString("book")
            val currencies = pairId.split("_".toRegex()).toTypedArray()
            if (currencies.size != 2) continue
            pairs.add(
                CurrencyPairInfo(
                    currencies[0].uppercase(Locale.ROOT),
                    currencies[1].uppercase(Locale.ROOT),
                    pairId
                ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        // Compatibility with previous version
        val pairId: String = checkerInfo.currencyPairId ?: "${checkerInfo.currencyBaseLowerCase}_${checkerInfo.currencyCounterLowerCase}"
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
            jsonObject.getJSONObject("payload").let {
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")

            ticker.bid = it.getDouble("bid")
            ticker.ask = it.getDouble("ask")

            ticker.vol = it.getDouble("volume")
            ticker.last = it.getDouble("last")

            ticker.timestamp = TimeUtils.convertISODateToTimestamp(it.getString("created_at"))
        }
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getJSONObject("error").getString("message")
    }
}