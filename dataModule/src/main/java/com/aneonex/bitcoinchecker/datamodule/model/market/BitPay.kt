package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class BitPay : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "BitPay"
        private const val TTS_NAME = "Bit Pay"
        private const val URL = "https://bitpay.com/api/rates/%1\$s/%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://bitpay.com/api/rates/%1\$s"

        private val supportedBaseCurrencies = arrayOf("BTC", "ETH", "XRP", "BCH")
    }

    override val currencyPairsNumOfRequests: Int
        get() = supportedBaseCurrencies.size

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return String.format(URL_CURRENCY_PAIRS, supportedBaseCurrencies[requestId])
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val baseCurrency = supportedBaseCurrencies[requestId]

        JSONArray(responseString).forEachJSONObject { quoteCurrencyJson ->
            val quoteCurrency = quoteCurrencyJson.getString("code")
            if(quoteCurrency != baseCurrency){
                pairs.add( CurrencyPairInfo(
                        baseCurrency,
                        quoteCurrency,
                        null
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.last = jsonObject.getDouble("rate")
    }
}