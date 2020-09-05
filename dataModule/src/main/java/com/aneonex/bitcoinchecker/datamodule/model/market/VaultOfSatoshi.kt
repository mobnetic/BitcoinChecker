package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class VaultOfSatoshi : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataObject = jsonObject.getJSONObject("data")
        ticker.vol = getDoubleFromMtgoxFormatObject(dataObject, "volume_1day")
        ticker.high = getDoubleFromMtgoxFormatObject(dataObject, "max_price")
        ticker.low = getDoubleFromMtgoxFormatObject(dataObject, "min_price")
        ticker.last = getDoubleFromMtgoxFormatObject(dataObject, "closing_price")
        ticker.timestamp = dataObject.getLong("date")
    }

    @Throws(Exception::class)
    private fun getDoubleFromMtgoxFormatObject(jsonObject: JSONObject, key: String): Double {
        val innerObject = jsonObject.getJSONObject(key)
        return innerObject.getDouble("value")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonArray = jsonObject.getJSONArray("data")
        val virtualCurrencies = ArrayList<String>()
        val currencies = ArrayList<String>()
        for (i in 0 until dataJsonArray.length()) {
            val currencyJsonObject = dataJsonArray.getJSONObject(i)
            if (currencyJsonObject.getInt("virtual") != 0) virtualCurrencies.add(currencyJsonObject.getString("code")) else currencies.add(currencyJsonObject.getString("code"))
        }
        val virtualCurrenciesCount = virtualCurrencies.size
        val currenciesCount = currencies.size
        for (i in 0 until virtualCurrenciesCount) {
            for (j in 0 until currenciesCount) {
                pairs.add(CurrencyPairInfo(virtualCurrencies[i], currencies[j], null))
            }
            for (j in 0 until virtualCurrenciesCount) {
                if (i != j) pairs.add(CurrencyPairInfo(virtualCurrencies[i], virtualCurrencies[j], null))
            }
        }
    }

    companion object {
        private const val NAME = "VaultOfSatoshi"
        private const val TTS_NAME = "Vault Of Satoshi"
        private const val URL = "https://api.vaultofsatoshi.com/public/ticker?order_currency=%1\$s&payment_currency=%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.vaultofsatoshi.com/public/currency"
    }
}