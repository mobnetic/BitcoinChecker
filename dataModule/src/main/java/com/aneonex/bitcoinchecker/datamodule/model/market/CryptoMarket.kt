package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

class CryptoMarket : SimpleMarket(
        "CryptoMarket",
        "https://api.cryptomkt.com/v1/market",
        "https://api.cryptomkt.com/v1/ticker?market=%1\$s",
        "Crypto Market"
        ) {

    companion object{
        private val templateAssets = arrayOf("BTC", "ETH", "EUR", "MXN", "BRL", "ARS", "CLP")

        private fun tryParseCurrencyPair(pairCode: String): Array<String> {
            for(templateAsset in templateAssets){
                if(pairCode.endsWith(templateAsset)) {
                    return arrayOf(
                            pairCode.substring(0, pairCode.length-templateAsset.length), // base asset
                            templateAsset // quote asset
                    )
                } else
                    if (pairCode.startsWith(templateAsset)){
                        return arrayOf(
                                templateAsset, // base asset
                                pairCode.substring(templateAsset.length, pairCode.length) // quote asset
                        )
                    }
            }

            return arrayOf()
        }
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("data")

        for (i in 0 until markets.length()) {
            val market = markets.getString(i)
            val assets = tryParseCurrencyPair(market)
            if(assets.size == 2) {
                pairs.add(CurrencyPairInfo(
                        assets[0], // base
                        assets[1], // quote
                        market
                ))
            }
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject
            .getJSONArray("data").getJSONObject(0)
            .let {
                ticker.timestamp = TimeUtils.convertISODateToTimestamp(it.getString("timestamp") + "Z")
                ticker.last = it.getDouble("last_price")
                ticker.high = it.getDouble("high")
                ticker.low = it.getDouble("low")
                ticker.vol = it.getDouble("volume")

                ticker.bid = it.getDouble("bid")
                ticker.ask = it.getDouble("ask")
            }
    }
}