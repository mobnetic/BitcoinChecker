package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency.AUD
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency.NZD
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency.USD
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.util.*

class IndependentReserve : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Independent Reserve"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.independentreserve.com/Public/GetMarketSummary?primaryCurrencyCode=%1\$s&secondaryCurrencyCode=%2\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.independentreserve.com/Public/GetValidPrimaryCurrencyCodes"
        private val SUPPORTED_SECONDARY_CURRENCY_CODES = listOf(AUD, USD, NZD)

        private fun getCurrencyPublicName(currency: String): String{
            return if(currency == "XBT")  VirtualCurrency.BTC else currency
        }
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val primaryCurrencies = JSONArray(responseString)

        for (i in 0 until primaryCurrencies.length()) {
            val primaryCurrencyCode = primaryCurrencies.getString(i)

            SUPPORTED_SECONDARY_CURRENCY_CODES.forEach { secondaryPair ->
                pairs.add(CurrencyPairInfo(
                        getCurrencyPublicName(primaryCurrencyCode.toUpperCase(Locale.ROOT)),
                        secondaryPair.toUpperCase(Locale.ROOT),
                        primaryCurrencyCode.toLowerCase(Locale.ROOT),
                ))
            }
        }
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("CurrentHighestBidPrice")
        ticker.vol = jsonObject.getDouble("DayVolumeXbt")
        ticker.high = jsonObject.getDouble("CurrentHighestBidPrice")
        ticker.low = jsonObject.getDouble("CurrentLowestOfferPrice")
        ticker.last = jsonObject.getDouble("LastPrice")
        ticker.timestamp = Instant.parse(jsonObject.getString("CreatedTimestampUtc")).toEpochMilli()
    }
}
