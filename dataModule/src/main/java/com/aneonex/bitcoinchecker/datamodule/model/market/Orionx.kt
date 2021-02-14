package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.*
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.pow

class Orionx : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Orionx"
        private const val TTS_NAME = NAME
        private const val URL_GRAPHQL = "https://client.orionx.com/graphql"
        private val requestHeaders = mapOf( "fingerprint" to "cualquierfingerprint")
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_GRAPHQL
    }

    // Get top liquid pairs GraphQl query
    override fun getCurrencyPairsPostRequestInfo(requestId: Int): PostRequestInfo {
        val query =
            "{\"query\":\""+
            "query {markets{ code mainCurrency {code} secondaryCurrency {code} }}" +
            "\"}"

        return PostRequestInfo(query, requestHeaders)
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONObject("data").getJSONArray("markets")

        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            val mainCurrency = market.getJSONObject("mainCurrency").getString("code")
            val secondaryCurrency = market.getJSONObject("secondaryCurrency").getString("code")

            pairs.add( CurrencyPairInfo(
                    mainCurrency,
                    secondaryCurrency,
                    market.getString("code")
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL_GRAPHQL
    }

    // Get pair ticker GraphQl query
    override fun getPostRequestInfo(requestId: Int, checkerInfo: CheckerInfo): PostRequestInfo {
        val query =
            "{\"query\":\" query getTicker(\$marketCode: ID!){" +
            "  marketCurrentStats(marketCode: \$marketCode, aggregation: d1) {close high low volume }" +
            "  market(code: \$marketCode){ mainCurrency{units} secondaryCurrency{units} }" +
            "}\"" +
            ",\"variables\":{\"marketCode\":\"${checkerInfo.currencyPairId}\"}"+
            "}"

        return PostRequestInfo(query, requestHeaders)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJson = jsonObject.getJSONObject("data")
        val marketJson = dataJson.getJSONObject("market")

        fun getCurrencyDecimals(currencyType: String): Double{
            return marketJson
                    .getJSONObject(currencyType)
                    .getInt("units")
                    .let {
                        10.0.pow(it.toDouble())
                    }
        }

        val baseCurrencyDecimals = getCurrencyDecimals("mainCurrency")
        val quoteCurrencyDecimals = getCurrencyDecimals("secondaryCurrency")

        dataJson
            .getJSONObject("marketCurrentStats")
            .apply {
                ticker.vol = getLong("volume").also {
                    if(it <= 0) throw Exception("No trading volume")
                } / baseCurrencyDecimals
                ticker.last = getLong("close") / quoteCurrencyDecimals
                ticker.high = getLong("high") / quoteCurrencyDecimals
                ticker.low = getLong("low") / quoteCurrencyDecimals
            }
    }
}