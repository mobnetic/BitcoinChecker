package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

class Mercado : Market(NAME, TTS_NAME, getCurrencyPairs()) {
    companion object {
        private const val NAME = "Mercado Bitcoin"
        private const val TTS_NAME = "Mercado"
        private const val URL = "https://www.mercadobitcoin.net/api/%1\$s/ticker/"

        @Suppress("SpellCheckingInspection")
        private fun getCurrencyPairs(): CurrencyPairsMap {
            // API Doc: https://www.mercadobitcoin.com.br/api-doc/
            val baseCurrencies = arrayOf(
                    "ASRFT",
                    "ATMFT",
                    VirtualCurrency.BCH,
                    VirtualCurrency.BTC,
                    "CAIFT",
                    "CHZ",
                    VirtualCurrency.ETH,
                    "GALFT",
                    "IMOB01",
                    "JUVFT",
                    VirtualCurrency.LINK,
                    VirtualCurrency.LTC,
                    "MBCONS01",
                    "MBCONS02",
                    "MBFP01",
                    "MBVASCO01",

                    "MBPRK01",
                    "MBPRK02",
                    "MBPRK03",
                    "MBPRK04",

                    "PAXG",
                    "PSGFT",
                    VirtualCurrency.USDC,
                    "WBX",
                    VirtualCurrency.XRP,
            )

            val quoteCurrencies = arrayOf(Currency.BRL)
            return baseCurrencies.associateTo(CurrencyPairsMap()) { it to quoteCurrencies }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase)
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
        ticker.timestamp = tickerJsonObject.getLong("date") * TimeUtils.MILLIS_IN_SECOND
    }
}