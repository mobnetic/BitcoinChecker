package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Zebpay : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Zebpay"
        private const val TTS_NAME = "Zeb Pay"
        private const val URL = "https://www.zebapi.com/api/v1/market/ticker-new/%1\$s/%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.AE] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.BAT] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    VirtualCurrency.TUSD
            )
            CURRENCY_PAIRS[VirtualCurrency.BTG] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.TUSD
            )
            CURRENCY_PAIRS[VirtualCurrency.CMT] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.EOS] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.GNT] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.IOST] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.KNC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.NCASH] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.XRP
            )
            CURRENCY_PAIRS[VirtualCurrency.OMG] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.REP] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.TRX] = arrayOf(
                    VirtualCurrency.BTC,
                    VirtualCurrency.XRP
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.ZIL] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.ZRX] = arrayOf(
                    VirtualCurrency.BTC
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("sell")
        ticker.ask = jsonObject.getDouble("buy")
        ticker.vol = jsonObject.getDouble("volume")
        ticker.last = jsonObject.getDouble("market")
    }
}