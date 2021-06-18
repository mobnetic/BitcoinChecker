package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.forEachString
import org.json.JSONObject

class BitcoinVenezuela : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitcoinVenezuela"
        private const val TTS_NAME = "Bitcoin Venezuela"
        private const val URL = "https://api.bitcoinvenezuela.com/?html=no&currency=%1\$s&amount=1&to=%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.bitcoinvenezuela.com/"
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.last = responseString.trim { it <= ' ' }.toDouble()
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        parseCurrencyPairsFromCurrencyBase(VirtualCurrency.BTC, jsonObject, pairs)
        parseCurrencyPairsFromCurrencyBase(VirtualCurrency.LTC, jsonObject, pairs)
        parseCurrencyPairsFromCurrencyBase(VirtualCurrency.XMR, jsonObject, pairs)
        parseCurrencyPairsFromCurrencyBase(VirtualCurrency.ETH, jsonObject, pairs)
    }

    @Throws(Exception::class)
    private fun parseCurrencyPairsFromCurrencyBase(currencyBase: String, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        if (!jsonObject.has(currencyBase)) return
        jsonObject.getJSONObject(currencyBase).names()!!.forEachString { quoteCurrencyName ->
            pairs.add(
                CurrencyPairInfo(
                    currencyBase,
                    quoteCurrencyName,
                    null
                )
            )
        }
    }
}