package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class BitBay : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BitBay.net"
        private const val TTS_NAME = "Bit Bay"
        private const val URL = "https://bitbay.net/API/Public/%1\$s%2\$s/ticker.json"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BCC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.DASH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.GAME] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.LSK] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR,
                    Currency.GBP
            )
            CURRENCY_PAIRS[VirtualCurrency.OMG] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.BTG] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.BAT] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.XLM] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
            CURRENCY_PAIRS[VirtualCurrency.TRX] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.PLN,
                    Currency.USD,
                    Currency.EUR
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("bid")
        ticker.ask = jsonObject.getDouble("ask")
        ticker.vol = jsonObject.getDouble("volume")
        if(jsonObject.has("max"))
            ticker.high = jsonObject.getDouble("max")
        if(jsonObject.has("min"))
            ticker.low = jsonObject.getDouble("min")
        ticker.last = jsonObject.getDouble("last")
    }
}