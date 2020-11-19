package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONArray
import org.json.JSONObject

class XthetaGlobal : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Xtheta Global"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.xthetaglobal.com/openapi/quote/v1/ticker/24hr?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.xthetaglobal.com/openapi/quote/v1/ticker/24hr"


        private val templateAssets = arrayOf("BTC", "ETH", "USDT")

        private fun tryParseSymbol(symbol: String): Array<String> {
            for(templateAsset in templateAssets){
                if(symbol.endsWith(templateAsset)) {
                    return arrayOf(
                        symbol.substring(0, symbol.length-templateAsset.length), // base asset
                        templateAsset // quote asset
                    )
                } else
                    if (symbol.startsWith(templateAsset)){
                        return arrayOf(
                                templateAsset, // base asset
                                symbol.substring(templateAsset.length, symbol.length) // quote asset
                        )
                    }
            }

            return arrayOf()
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val markets = JSONArray(responseString)

        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            val symbol = market.getString("symbol")

            val assets = tryParseSymbol(symbol)
            if(assets.size != 2)
                continue

            pairs.add( CurrencyPairInfo(
                    assets[0], // Base asset
                    assets[1], // Quote asset
                    symbol
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.apply {
            ticker.ask = getDouble("bestAskPrice")
            ticker.bid = getDouble("bestBidPrice")
            ticker.high = getDouble("highPrice")
            ticker.low = getDouble("lowPrice")
            ticker.last = getDouble("lastPrice")
            ticker.vol = getDouble("volume")
            ticker.timestamp = getLong("time")
        }
    }
}