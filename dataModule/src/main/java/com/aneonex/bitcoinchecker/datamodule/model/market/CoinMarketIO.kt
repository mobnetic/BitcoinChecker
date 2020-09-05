package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class CoinMarketIO : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "CoinMarket.io"
        private const val TTS_NAME = "Coin Market IO"
        private const val URL = "https://coinmarket.io/ticker/%1\$s%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.LEAF] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.USDE] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.DGB] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.KDC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.CON] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.NOBL] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.SMC] = arrayOf(
                    VirtualCurrency.BTC
            )
            //		CURRENCY_PAIRS.put(VirtualCurrency.PRT, new String[]{
//				VirtualCurrency.BTC
//			});
            CURRENCY_PAIRS[VirtualCurrency.VTC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.UTC] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.KARM] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.RDD] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.RPD] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.ICN] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.PENG] = arrayOf(
                    VirtualCurrency.BTC
            )
            CURRENCY_PAIRS[VirtualCurrency.MINT] = arrayOf(
                    VirtualCurrency.BTC
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.vol = jsonObject.getDouble("volume24")
        ticker.last = jsonObject.getDouble("last")
    }
}