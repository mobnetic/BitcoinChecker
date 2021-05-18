package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.util.*

class IndependentReserve : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Independent Reserve"
        private const val TTS_NAME = NAME
        private const val URL_TICKER = "https://api.independentreserve.com/Public/GetMarketSummary?primaryCurrencyCode=%1\$s&secondaryCurrencyCode=%2\$s"
        private const val URL_CURRENCY_PRIMARY_CODES = "https://api.independentreserve.com/Public/GetValidPrimaryCurrencyCodes"
        private const val URL_CURRENCY_SECONDARY_CODES = "https://api.independentreserve.com/Public/GetValidSecondaryCurrencyCodes"

        private fun getCurrencyPublicName(currency: String): String{
            return if(currency == "XBT")  VirtualCurrency.BTC else currency
        }
    }

    private val latestPrimaryCurrencies = mutableListOf<String>()

    override val currencyPairsNumOfRequests: Int = 2

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return if (requestId == 0) URL_CURRENCY_PRIMARY_CODES else URL_CURRENCY_SECONDARY_CODES
    }


    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val currencyCodes = JSONArray(responseString)

        // Handling primary currencies
        if (requestId == 0) {
            latestPrimaryCurrencies.clear()

            for (i in 0 until currencyCodes.length()) {
                latestPrimaryCurrencies.add(currencyCodes.getString(i))
            }
        }
        // Handling secondary currencies
        else {
            for (i in 0 until currencyCodes.length()) {
                val secondaryCurrencyCode = currencyCodes.getString(i)

                latestPrimaryCurrencies.forEach { primaryCurrency ->
                    pairs.add(
                        CurrencyPairInfo(
                            getCurrencyPublicName(primaryCurrency.uppercase(Locale.ROOT)),
                            secondaryCurrencyCode.uppercase(Locale.ROOT),
                            null
                        ))
                }
            }

            latestPrimaryCurrencies.clear()
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL_TICKER, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("CurrentHighestBidPrice")
        ticker.ask = jsonObject.getDouble("CurrentLowestOfferPrice")

        ticker.high = jsonObject.getDouble("DayHighestPrice")
        ticker.low = jsonObject.getDouble("DayLowestPrice")

        ticker.last = jsonObject.getDouble("LastPrice")
        @Suppress("SpellCheckingInspection")
        ticker.vol = jsonObject.getDouble("DayVolumeXbtInSecondaryCurrrency")

        ticker.timestamp = Instant.parse(jsonObject.getString("CreatedTimestampUtc")).toEpochMilli()
    }
}
