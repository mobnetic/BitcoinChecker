package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class Vaultoro : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Vaultoro"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.vaultoro.com/v1/public/ohlc?pair=%1\$s&since=%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[Currency.GOLD] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.DASH
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        var pairId = checkerInfo.currencyPairId
        if(pairId == null){
            pairId = "${checkerInfo.currencyBase}${checkerInfo.currencyCounter}"
        }

        val sinceLastWeekTime = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)) / 1000
        return String.format(URL, pairId, sinceLastWeekTime)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJson = jsonObject.getJSONArray("data")
        if(dataJson.length() == 0) throw MarketParseException("No data")

        val tickerJson = dataJson.getJSONObject(0)
        ticker.last = tickerJson.getDouble("close")
        ticker.high = tickerJson.getDouble("high")
        ticker.low = tickerJson.getDouble("low")
        ticker.vol = tickerJson.getDouble("volume")
        ticker.timestamp = tickerJson.getLong("createdAt") * 1000
    }
}