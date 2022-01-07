package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class Phemex : SimpleMarket(
    "Phemex",
    "https://api.phemex.com/exchange/public/cfg/v2/products",
    "https://api.phemex.com/md/spot/ticker/24hr?symbol=%1\$s"
) {

    override fun parseCurrencyPairsFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        jsonObject
            .getJSONObject("data")
            .getJSONArray("products")
            .forEachJSONObject { market ->
                if (market.getString("type") == "Spot") {
                    pairs.add(
                        CurrencyPairInfo(
                            market.getString("baseCurrency"),
                            market.getString("quoteCurrency"),
                            market.getString("symbol")
                        )
                    )
                }
            }
    }

    override fun parseTickerFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        ticker: Ticker,
        checkerInfo: CheckerInfo
    ) {
        jsonObject
            .getJSONObject("result")
            .let { jsonTicker ->
                ticker.apply {
                    last = jsonTicker.getValueEp("lastEp")

                    high = jsonTicker.getValueEp("highEp")
                    low = jsonTicker.getValueEp("lowEp")

                    vol = jsonTicker.getValueEp("volumeEv")
                    volQuote = jsonTicker.getValueEp("turnoverEv")

                    bid = jsonTicker.getValueEp("bidEp")
                    ask = jsonTicker.getValueEp("askEp")

                    timestamp = jsonTicker.getLong("timestamp") / 1_000_000
                }
            }
    }

    companion object {
        private fun JSONObject.getValueEp(name: String): Double {
            return getLong(name) / 100_000_000.0
        }
    }
}