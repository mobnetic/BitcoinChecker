package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject
import java.time.format.DateTimeFormatter
import java.util.*

class BinanceFutures : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Binance Futures"
        private const val TTS_NAME = NAME

        private const val URL_USD_M = "https://fapi.binance.com/fapi/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS_USD_M = "https://fapi.binance.com/fapi/v1/exchangeInfo"

        private const val URL_COIN_M = "https://dapi.binance.com/dapi/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS_COIN_M = "https://dapi.binance.com/dapi/v1/exchangeInfo"

        private const val COIN_M_PREFIX = "2:"

        private fun isCoinMPair(pairId: String) = pairId.startsWith(COIN_M_PREFIX)

        private val FUTURES_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd", Locale.ROOT)

        private fun parseTicker(jsonObject: JSONObject, ticker: Ticker) {
            jsonObject.apply {
                ticker.vol = getDouble("volume")
                ticker.high = getDouble("highPrice")
                ticker.low = getDouble("lowPrice")
                ticker.last = getDouble("lastPrice")
                ticker.timestamp = getLong("closeTime")

                // Optional
                ticker.volQuote = optDouble("quoteVolume", ticker.volQuote)
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val utlTemplate: String
        val pairId: String

        if(isCoinMPair(checkerInfo.currencyPairId!!)) {
            pairId = checkerInfo.currencyPairId.substring(COIN_M_PREFIX.length)
            // String.format(URL_COIN_M, pairId)
            utlTemplate = URL_COIN_M
        }
        else {
//            String.format(URL_USD_M, checkerInfo.currencyPairId)
            pairId = checkerInfo.currencyPairId
            utlTemplate = URL_USD_M
        }

        val deliveryDate = FuturesContractType.getDeliveryDate(checkerInfo.contractType) ?:
            return String.format(utlTemplate, pairId)

        val pairIdWithDeliveryDate = "${checkerInfo.currencyBase}${checkerInfo.currencyCounter}_${FUTURES_DATE_FORMAT.format(deliveryDate)}"
        return String.format(utlTemplate, pairIdWithDeliveryDate)
    }

    override fun parseTicker(
        requestId: Int,
        responseString: String,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        if(isCoinMPair(checkerInfo.currencyPairId!!))
            parseTicker(JSONArray(responseString).getJSONObject(0), ticker)
        else
            parseTicker(JSONObject(responseString), ticker)
    }

    override val currencyPairsNumOfRequests: Int
        get() = 2

    override fun getCurrencyPairsUrl(requestId: Int): String =
        if(requestId == 0) URL_CURRENCY_PAIRS_USD_M else URL_CURRENCY_PAIRS_COIN_M

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        fun parseContractType(value: String): FuturesContractType? =
            when(value) {
                "PERPETUAL" -> FuturesContractType.PERPETUAL
                "CURRENT_QUARTER" -> FuturesContractType.QUARTERLY
                "NEXT_QUARTER" -> FuturesContractType.BIQUARTERLY
                else -> null
            }

        jsonObject.getJSONArray("symbols").forEachJSONObject { marketJsonObject ->
            // Tha app UI supports only perpetual futures
            val contractType = parseContractType(marketJsonObject.getString("contractType"))
                ?: return@forEachJSONObject
            //if(marketJsonObject.getString("contractType") != "PERPETUAL")
            //    return@forEachJSONObject

            val symbol = marketJsonObject.getString("symbol").let {
                if(requestId > 0) COIN_M_PREFIX + it else it
            }
            val baseAsset = marketJsonObject.getString("baseAsset")
            val quoteAsset = marketJsonObject.getString("quoteAsset")

            pairs.add(CurrencyPairInfo(
                baseAsset,
                quoteAsset,
                symbol,
                contractType))
        }
    }
}