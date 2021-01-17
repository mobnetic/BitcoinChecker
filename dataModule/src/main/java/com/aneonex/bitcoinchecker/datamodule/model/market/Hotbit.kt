package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class Hotbit : SimpleMarket(
        "Hotbit",
        "https://api.hotbit.io/api/v1/market.list",
        "https://api.hotbit.io/api/v1/market.status?market=%1\$s&period=86400" // 24 hours status
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val symbols = jsonObject.getJSONArray("result")
        for (i in 0 until symbols.length()) {
            val market = symbols.getJSONObject(i)

            pairs.add(CurrencyPairInfo(
                market.getString("stock"),
                market.getString("money"),
                null))
        }
    }

    override fun getPairId(checkerInfo: CheckerInfo): String {
        return "${checkerInfo.currencyBase}/${checkerInfo.currencyCounter}"
    }

    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getJSONObject("error").getString("message")
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("result").let {
            ticker.vol = it.getDouble("volume")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("last")
        }
    }
}