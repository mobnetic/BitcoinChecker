package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.R
import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class UniswapV2 : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Uniswap (v2)"
        private const val TTS_NAME = "Uniswap v2"
        private const val URL = "https://api.thegraph.com/subgraphs/name/uniswap/uniswap-v2"
        private const val URL_CURRENCY_PAIRS = URL

        private fun getStableCoinWeight(symbol: String): Int {
            return when(symbol){
                "WETH", "WBTC" -> 1
                "USDC", "USDT", "EURS" -> 2
                else -> 0
            }
        }
    }

    override val cautionResId: Int get() = R.string.market_caution_uniswap

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    // Get top liquid pairs GraphQl query
    override fun getCurrencyPairsPostRequestInfo(requestId: Int): PostRequestInfo {
        val body = "{\"query\":\"{pairs(first: 500, orderBy:reserveUSD, orderDirection:desc) {id token0{symbol} token1{symbol}}}\"}"
        return PostRequestInfo( body )
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val addedPairs = HashSet<String>()

        jsonObject
            .getJSONObject("data")
            .getJSONArray("pairs")
            .forEachJSONObject { market ->

            val token0 = market.getJSONObject("token0").getString("symbol")
            val token1 = market.getJSONObject("token1").getString("symbol")

            val token0BaseWeight = getStableCoinWeight(token0)
            val token1BaseWeight = getStableCoinWeight(token1)

            val pairId = market.getString("id")

            val baseSymbol = if(token1BaseWeight < token0BaseWeight) token1 else token0
            val quoteSymbol = if(token1BaseWeight < token0BaseWeight) token0 else token1

            if(!addedPairs.add("$baseSymbol:$quoteSymbol"))
                return@forEachJSONObject // already added most liquid version

            pairs.add( CurrencyPairInfo(baseSymbol, quoteSymbol, pairId))

            if(token1BaseWeight == token0BaseWeight) {
                // Adding reverse pair

                if(!addedPairs.add("$quoteSymbol:$baseSymbol"))
                    return@forEachJSONObject // already added most liquid version

                pairs.add(CurrencyPairInfo(quoteSymbol, baseSymbol, pairId))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    // Get pair ticker GraphQl query
    override fun getPostRequestInfo(requestId: Int, checkerInfo: CheckerInfo): PostRequestInfo {
        val body = "{\"query\":\"{"+
                // Get pair price
                "pair(id: \\\"${checkerInfo.currencyPairId}\\\"){token0{symbol} token1Price token0Price} " +
                // Get pair timestamp
                "swaps(first: 1, where: { pair: \\\"${checkerInfo.currencyPairId}\\\" } orderBy: timestamp, orderDirection: desc) {timestamp}"+
                "}\"}"

        return PostRequestInfo(body)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJson = jsonObject.getJSONObject("data")
        val pairJson = dataJson.getJSONObject("pair")

        val token0 = pairJson.getJSONObject("token0").getString("symbol")
        val isReversePair = token0 != checkerInfo.currencyBase

        ticker.last = if(isReversePair) pairJson.getDouble("token0Price") else pairJson.getDouble("token1Price")

        val swaps = dataJson.getJSONArray("swaps")
        if(swaps.length() > 0) {
            ticker.timestamp = swaps.getJSONObject(0).getLong("timestamp")
        }
    }
}