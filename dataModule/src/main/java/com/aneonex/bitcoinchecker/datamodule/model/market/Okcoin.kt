package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Okcoin : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "OKCoin"
        private const val TTS_NAME = "OK Coin"
        private const val URL_USD = "https://www.okcoin.com/api/v1/ticker.do?symbol=%1\$s_%2\$s"
        private const val URL_CNY = "https://www.okcoin.cn/api/v1/ticker.do?symbol=%1\$s_%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.CNY,
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.CNY,
                    Currency.USD
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return if (Currency.USD == checkerInfo.currencyCounter) {
            String.format(URL_USD, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
        } else {
            String.format(URL_CNY, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = tickerJsonObject.getDouble("buy")
        ticker.ask = tickerJsonObject.getDouble("sell")
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
    }
}