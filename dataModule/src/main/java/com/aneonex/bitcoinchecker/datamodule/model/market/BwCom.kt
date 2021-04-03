package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject
import java.util.*

class BwCom : SimpleMarket(
        "BW.com",
        "https://www.bw.com/exchange/config/controller/website/marketcontroller/getByWebId",
        "https://www.bw.com/api/data/v1/ticker?marketId=%1\$s"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("datas")
        for (i in 0 until markets.length()) {
            val market = markets.getJSONObject(i)

            val assets = market.getString("name").toUpperCase(Locale.ROOT).split('_')
            if(assets.size != 2) continue

            pairs.add(CurrencyPairInfo(
                assets[0], // Base currency
                assets[1], // Quote currency
                market.getString("marketId")
            ))
        }
    }

    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getJSONObject("resMsg").getString("message")
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONArray("datas").let {
            ticker.last = it.getDouble(1)
            ticker.high = it.getDouble(2)
            ticker.low = it.getDouble(3)
            ticker.vol = it.getDouble(4)

            ticker.bid = it.getDouble(7)
            ticker.ask = it.getDouble(8)
        }
    }
}